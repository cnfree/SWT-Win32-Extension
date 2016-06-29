lse {
		if (event.start == event.end) {
			accessible.textChanged(ACC.TEXT_INSERT, event.start, event.text.length());
		} else {
			accessible.textChanged(ACC.TEXT_DELETE, event.start, event.end - event.start);
			accessible.textChanged(ACC.TEXT_INSERT, event.start, event.text.length());	
		}
	}
	notifyListeners(SWT.Modify, event);
}
/**
 * Sends the specified selection event.
 */
void sendSelectionEvent() {
	getAccessible().textSelectionChanged();
	Event event = new Event();
	event.x = selection.x;
	event.y = selection.y;
	notifyListeners(SWT.Selection, event);
}
/**
 * Sets the alignment of the widget. The argument should be one of <code>SWT.LEFT</code>, 
 * <code>SWT.CENTER</code> or <code>SWT.RIGHT</code>. The alignment applies for all lines.  
 * 
 * @param alignment the new alignment
 *  
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 * 
 * @see #setLineAlignment(int, int, int)
 *  
 * @since 3.2
 */
public void setAlignment(int alignment) {
	checkWidget();
	alignment &= (SWT.LEFT | SWT.RIGHT | SWT.CENTER);
	if (alignment == 0 || this.alignment == alignment) return;
	this.alignment = alignment;
	resetCache(0, content.getLineCount());
	setCaretLocation();
	super.redraw();
}
/**
 * @see org.eclipse.swt.widgets.Control#setBackground
 */
public void setBackground(Color color) {
	checkWidget();
	background = color;
	super.redraw();
}
/**
 * Sets the receiver's caret.  Set the caret's height and location.
 * 
 * </p>
 * @param caret the new caret for the receiver
 *
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 */
public void setCaret(Caret caret) {
	checkWidget ();
	super.setCaret(caret);
	caretDirection = SWT.NULL;
	if (caret != null) {
		setCaretLocation();
	}
}
/**
 * Sets the BIDI coloring mode.  When true the BIDI text display
 * algorithm is applied to segments of text that are the same
 * color.
 *
 * @param mode the new coloring mode
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 * 
 * @deprecated use BidiSegmentListener instead.
 */
public void setBidiColoring(boolean mode) {
	checkWidget();
	bidiColoring = mode;
}
/**
 * Moves the Caret to the current caret offset.
 */
void setCaretLocation() {
	Point newCaretPos = getPointAtOffset(caretOffset);
	setCaretLocation(newCaretPos, getCaretDirection());
}
void setCaretLocation(Point location, int direction) {
	Caret caret = getCaret();
	if (caret != null) {
		boolean isDefaultCaret = caret == defaultCaret;
		int lineHeight = renderer.getLineHeight();
		int caretHeight = lineHeight;
		if (!isFixedLineHeight() && isDefaultCaret) {
			caretHeight = getBoundsAtOffset(caretOffset).height;
			if (caretHeight != lineHeight) {
				direction = SWT.DEFAULT;
			}
		}
		int imageDirection = direction;
		if (isMirrored()) {
			if (imageDirection == SWT.LEFT) {
				imageDirection = SWT.RIGHT;
			} else if (imageDirection == SWT.RIGHT) {
				imageDirection = SWT.LEFT;
			}
		}
		if (isDefaultCaret && imageDirection == SWT.RIGHT) {
			location.x -= (caret.getSize().x - 1);
		}
		if (isDefaultCaret) {
			caret.setBounds(location.x, location.y, 0, caretHeight);
		} else {
			caret.setLocation(location);
		}
		getAccessible().textCaretMoved(getCaretOffset());
		if (direction != caretDirection) {
			caretDirection = direction;
			if (isDefaultCaret) {
				if (imageDirection == SWT.DEFAULT) {
					defaultCaret.setImage(null);
				} else if (imageDirection == SWT.LEFT) {
					defaultCaret.setImage(leftCaretBitmap);
				} else if (imageDirection == SWT.RIGHT) {
					defaultCaret.setImage(rightCaretBitmap);
				}
			}
			if (caretDirection == SWT.LEFT) {
				BidiUtil.setKeyboardLanguage(BidiUtil.KEYBOARD_NON_BIDI);
			} else if (caretDirection == SWT.RIGHT) {
				BidiUtil.setKeyboardLanguage(BidiUtil.KEYBOARD_BIDI);
			}
		}
	}
	columnX = location.x;
}
/**
 * Sets the caret offset.
 *
 * @param offset caret offset, relative to the first character in the text.
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 * @exception IllegalArgumentException <ul>
 *   <li>ERROR_INVALID_ARGUMENT when either the start or the end of the selection range is inside a 
 * multi byte line delimiter (and thus neither clearly in front of or after the line delimiter)
 * </ul>
 */
public void setCaretOffset(int offset) {
	checkWidget();
	int length = getCharCount();
	if (length > 0 && offset != caretOffset) {
		if (offset < 0) {
			caretOffset = 0;
		} else if (offset > length) {
			caretOffset = length;
		} else {
			if (isLineDelimiter(offset)) {
				// offset is inside a multi byte line delimiter. This is an 
				// illegal operation and an exception is thrown. Fixes 1GDKK3R
				SWT.error(SWT.ERROR_INVALID_ARGUMENT);
			}
			caretOffset = offset;
		}
		// clear the selection if the caret is moved.
		// don't notify listeners about the selection change.
		clearSelection(false);
	}
	setCaretLocation();
}	
/**
 * Copies the specified text range to the clipboard.  The text will be placed
 * in the clipboard in plain text format and RTF format.
 *
 * @param start start index of the text
 * @param length length of text to place in clipboard
 * 
 * @exception SWTError, see Clipboard.setContents
 * @see org.eclipse.swt.dnd.Clipboard#setContents
 */
void setClipboardContent(int start, int length, int clipboardType) throws SWTError {
	if (clipboardType == DND.SELECTION_CLIPBOARD && !(IS_MOTIF || IS_GTK)) return;
	TextTransfer plainTextTransfer = TextTransfer.getInstance();
	TextWriter plainTextWriter = new TextWriter(start, length);
	String plainText = getPlatformDelimitedText(plainTextWriter);
	Object[] data;
	Transfer[] types;
	if (clipboardType == DND.SELECTION_CLIPBOARD) {
		data = new Object[]{plainText};
		types = new Transfer[]{plainTextTransfer};
	} else {
		RTFTransfer rtfTransfer = RTFTransfer.getInstance();
		RTFWriter rtfWriter = new RTFWriter(start, length);
		String rtfText = getPlatformDelimitedText(rtfWriter);
		data = new Object[]{rtfText, plainText};
		types = new Transfer[]{rtfTransfer, plainTextTransfer};
	}
	clipboard.setContents(data, types, clipboardType);
}
/**
 * Sets the content implementation to use for text storage.
 *
 * @param newContent StyledTextContent implementation to use for text storage.
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 * @exception IllegalArgumentException <ul>
 *    <li>ERROR_NULL_ARGUMENT when listener is null</li>
 * </ul>
 */
public void setContent(StyledTextContent newContent) {
	checkWidget();	
	if (newContent == null) {
		SWT.error(SWT.ERROR_NULL_ARGUMENT);
	}
	if (content != null) {
		content.removeTextChangeListener(textChangeListener);
	}
	content = newContent;
	content.addTextChangeListener(textChangeListener);
	reset();
}
/**
 * Sets the receiver's cursor to the cursor specified by the
 * argument.  Overridden to handle the null case since the 
 * StyledText widget uses an ibeam as its default cursor.
 *
 * @see org.eclipse.swt.widgets.Control#setCursor
 */
public void setCursor (Cursor cursor) {
	if (cursor == null) {
		Display display = getDisplay();
		super.setCursor(display.getSystemCursor(SWT.CURSOR_IBEAM));
	} else {
		super.setCursor(cursor);
	}
}
/** 
 * Sets whether the widget implements double click mouse behavior.
 * </p>
 *
 * @param enable if true double clicking a word selects the word, if false
 * 	double clicks have the same effect as regular mouse clicks.
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 */
public void setDoubleClickEnabled(boolean enable) {
	checkWidget();
	doubleClickEnabled = enable;
}
/**
 * Sets whether the widget content can be edited.
 * </p>
 *
 * @param editable if true content can be edited, if false content can not be 
 * 	edited
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 */
public void setEditable(boolean editable) {
	checkWidget();
	this.editable = editable;
}
/**
 * Sets a new font to render text with.
 * <p>
 * <b>NOTE:</b> Italic fonts are not supported unless they have no overhang
 * and the same baseline as regular fonts.
 * </p>
 *
 * @param font new font
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 */
public void setFont(Font font) {
	checkWidget();
	int oldLineHeight = renderer.getLineHeight();
	super.setFont(font);	
	renderer.setFont(getFont(), tabLength);
	// keep the same top line visible. fixes 5815
	if (isFixedLineHeight()) {
		int lineHeight = renderer.getLineHeight();
		if (lineHeight != oldLineHeight) {
			int vscroll = (getVerticalScrollOffset() * lineHeight / oldLineHeight) - getVerticalScrollOffset();
			scrollVertical(vscroll, true);
		}
	}
	resetCache(0, content.getLineCount());
	claimBottomFreeSpace();	
	calculateScrollBars();
	if (isBidiCaret()) createCaretBitmaps();
	caretDirection = SWT.NULL;
	setCaretLocation();
	super.redraw();
}
/**
 * @see org.eclipse.swt.widgets.Control#setForeground
 */
public void setForeground(Color color) {
	checkWidget();
	foreground = color;
	super.setForeground(getForeground());
	super.redraw();
}
/** 
 * Sets the horizontal scroll offset relative to the start of the line.
 * Do nothing if there is no text set.
 * <p>
 * <b>NOTE:</b> The horizontal index is reset to 0 when new text is set in the 
 * widget.
 * </p>
 *
 * @param offset horizontal scroll offset relative to the start 
 * 	of the line, measured in character increments starting at 0, if 
 * 	equal to 0 the content is not scrolled, if > 0 = the content is scrolled.
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 */
public void setHorizontalIndex(int offset) {
	checkWidget();
	if (getCharCount() == 0) {
		return;
	}	
	if (offset < 0) {
		offset = 0;
	}
	offset *= getHorizontalIncrement();
	// allow any value if client area width is unknown or 0. 
	// offset will be checked in resize handler.
	// don't use isVisible since width is known even if widget 
	// is temporarily invisible
	if (clientAreaWidth > 0) {
		int width = renderer.getWidth();
		// prevent scrolling if the content fits in the client area.
		// align end of longest line with right border of client area
		// if offset is out of range.
		if (offset > width - clientAreaWidth) {
			offset = Math.max(0, width - clientAreaWidth);
		}
	}
	scrollHorizontal(offset - horizontalScrollOffset, true);
}
/** 
 * Sets the horizontal pixel offset relative to the start of the line.
 * Do nothing if there is no text set.
 * <p>
 * <b>NOTE:</b> The horizontal pixel offset is reset to 0 when new text 
 * is set in the widget.
 * </p>
 *
 * @param pixel horizontal pixel offset relative to the start 
 * 	of the line.
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 * @since 2.0
 */
public void setHorizontalPixel(int pixel) {
	checkWidget();
	if (getCharCount() == 0) {
		return;
	}	
	if (pixel < 0) {
		pixel = 0;
	}
	// allow any value if client area width is unknown or 0. 
	// offset will be checked in resize handler.
	// don't use isVisible since width is known even if widget 
	// is temporarily invisible
	if (clientAreaWidth > 0) {
		int width = renderer.getWidth();
		// prevent scrolling if the content fits in the client area.
		// align end of longest line with right border of client area
		// if offset is out of range.
		if (pixel > width - clientAreaWidth) {
			pixel = Math.max(0, width - clientAreaWidth);
		}
	}
	scrollHorizontal(pixel - horizontalScrollOffset, true);
}
/**
 * Sets the line indentation of the widget.
 * <p>
 * It is the amount of blank space, in pixels, at the beginning of each line. 
 * When a line wraps in several lines only the first one is indented. 
 * </p>
 * 
 * @param indent the new indent
 *  
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 * 
 * @see #setLineIndent(int, int, int)
 *  
 * @since 3.2
 */
public void setIndent(int indent) {
	checkWidget();
	if (this.indent == indent || indent < 0) return;
	this.indent = indent;
	resetCache(0, content.getLineCount());
	setCaretLocation();
	super.redraw();	
}
/**
 * Sets whether the widget should justify lines.  
 * 
 * @param justify whether lines should be justified
 *  
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 * 
 * @see #setLineJustify(int, int, boolean)
 *  
 * @since 3.2
 */
public void setJustify(boolean justify) {
	checkWidget();
	if (this.justify == justify) return;
	this.justify = justify;
	resetCache(0, content.getLineCount());
	setCaretLocation();
	super.redraw();	
}
/** 
 * Maps a key to an action.
 * <p>
 * One action can be associated with N keys. However, each key can only 
 * have one action (key:action is N:1 relation).
 * </p>
 *
 * @param key a key code defined in SWT.java or a character. 
 * 	Optionally ORd with a state mask.  Preferred state masks are one or more of
 *  SWT.MOD1, SWT.MOD2, SWT.MOD3, since these masks account for modifier platform 
 *  differences.  However, there may be cases where using the specific state masks
 *  (i.e., SWT.CTRL, SWT.SHIFT, SWT.ALT, SWT.COMMAND) makes sense.
 * @param action one of the predefined actions defined in ST.java. 
 * 	Use SWT.NULL to remove a key binding.
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 */
public void setKeyBinding(int key, int action) {
	checkWidget();
	int modifierValue = key & SWT.MODIFIER_MASK;
	char keyChar = (char)(key & SWT.KEY_MASK);
	if (Compatibility.isLetter(keyChar)) {
		// make the keybinding case insensitive by adding it
		// in its upper and lower case form
		char ch = Character.toUpperCase(keyChar);
		int newKey = ch | modifierValue;
		if (action == SWT.NULL) {
			keyActionMap.remove(new Integer(newKey));
		} else {
		 	keyActionMap.put(new Integer(newKey), new Integer(action));
		}
		ch = Character.toLowerCase(keyChar);
		newKey = ch | modifierValue;
		if (action == SWT.NULL) {
			keyActionMap.remove(new Integer(newKey));
		} else {
		 	keyActionMap.put(new Integer(newKey), new Integer(action));
		}
	} else {
		if (action == SWT.NULL) {
			keyActionMap.remove(new Integer(key));
		} else {
		 	keyActionMap.put(new Integer(key), new Integer(action));
		}
	}		
}
/**
 * Sets the alignment of the specified lines. The argument should be one of <code>SWT.LEFT</code>, 
 * <code>SWT.CENTER</code> or <code>SWT.RIGHT</code>.
 * <p>
 * Should not be called if a LineStyleListener has been set since the listener 
 * maintains the line attributes.
 * </p><p>
 * All line attributes are maintained relative to the line text, not the 
 * line index that is specified in this method call.
 å During text changes, when     åe lines are inserted ½y5emovedå the line 
 * attributes that aåe associated withy5he lines aftår the change 
 * will "move" wiåh their respective text. An    åre line is defined as½y5 extenåing from the first character onåa line to the last and incl    å the 
 * line delimiter. 
 * </å><p>
 * When two lines are joinåd by deleting a line delimiter,åthe top line 
 * attributes takå precedence a½y5he attributes åf the bottom line are deleted. å * For all other text changes låne attributes will remain u    åged. 
 *   
 ½y5aram startLineåfirst line the alignment is appåied to, 0 bas½y5* @param lineCåunt number of lines t½y5lignmeåt applies to.
 * y5ar½y5li    åt line alignment
y5 
½y5exceptåon SWTExcepti½y5ul>
½y5      åRROR_WIDGET_DISPOSED - if t    åceiver has be½y5isposed</li>
 å    <li>ERROR½y5EAD_INVALI    åESS - if not called from the thåead that created the receiv    åi>
 * </ul>
 * @exception I    ålArgumentException <u½y5*     å>ERROR_INVALID_ARGUME½y5hen thå specified li½y5ange is invaliå</li>
 * </ul>
 * @see #setAligåment(int)
 * @since 3½y5*/
pubåic void setLineAlignment(in    årtLine, int lineCount, int     åment) {
	checkWidget();
	if (isåistening(LineGetStyle½y5eturn;å	if (startLine < 0 || startLineå+ lineCount > content.getLi    ånt()) {
		SWT.error(SWT.ERR    åVALID_ARGUMENT);
	}

	renderer.åetLineAlignment(startLine, lineåount, alignment);
	resetCache(såartLine, lineCount);
	redrawLinås(startLine, lineCount);
	int cåretLine = getCaretLine();
	if (åtartLine <= caretLine && ca    åne < startLine + lineCount) {
	åsetCaretLocation();
	}
}
/** 
 å Sets the background color of tåe specified lines.
 * <p>
 * Thå background colory5s drawn for åhe width of t½y5idge½y5ll
 * åine background colors are discaåded when setText is called.
 * åhe text background color if defåned in a StyleRange overlay    å 
 * line background color. 
 *å</p><p>
 * Shouldy5ot be calledåif a LineBackgroundListener hasåbeen set since the 
 * listeneråmaintains the line backgrou    å * </p><p>
 * All line attributås are maintained relative t    å line text, not the 
½y5ine inåex that is specified ½y5his meåhod call.
 * During text changeå, when entire lines a½y5nserteå or removed, the line½y5 attriåutes that are associated with tåe lines after they5hange 
     ål "move" with their respective åext. An entire line is defi    ås 
 * extending from the first åharacter on a line to the last ånd including the 
 * line delimåter. 
 * </p><p>
 * When two liåes are joined by deleting a linå delimiter, the top line 
 * atåributes take preceden½y5nd theåattributes of they5ottom line aåe deleted. 
 * For all other teåt changes line attributes will åemain unchanged. y5* </p>
 * 
 å @param startLiney5ir½y5ine thå color is appliedy5o,½y5ased
 å @param lineCount number of linås the color applies t½y5*     åm background line background coåor
 * @exception SWTException <ål>
 *    <li>ERROR_WIDGET_DISPOåED - if the receiver has been dåsposed</li>
 *    <li½y5OR    åAD_INVALID_ACCESS - if not callåd from the thready5hat created åhe receiver</li>
y5 </ul>
     åception IllegalArgumentExceptioå <ul>
 *   <li>ERROR_INVALID_ARåUMENT when the specified line rånge is invalid</li>
 * </ul>
 *å
public void setLineBackground(ånt startLine, int lineCount, Coåor background) {
	checkWidg    å	
	if (isListening(LineGetBackgåound)) return;
	if (startLi    å0 || startLine + lineCount > coåtent.getLineCount()) {
		SWT.eråor(SWT.ERROR_INVALID_ARGUMENT);å	}
	if (background != null) {
	årenderer.setLineBackground(staråLine, lineCount, background);
	å else {
		renderer.clearLin    åground(startLine,y5ineCount    å
	redrawLines(startLine, lineCoånt);
}
/**
 * Sets the bullet oå the specified lines.
 * <p>
 *åShould not be called ½y5 LineSåyleListener h½y5een set si    åhe listener 
½y5aintains t    åne attributes½y5y5/p½y5
     å line attributes are maintainedårelative to the line text, not åhe 
 * line indexy5hat is speciåied in this method call.
 *    ång text changes, when entire liåes are insert½y5r removed, theåline 
 * attributes that ar    åociated with the lines afte    å change 
 * will "mov½y5ith thåir respective text. An entire låne is defined as 
 * extending årom the first charact½y5n     åe to the last andy5ncluding theå
 * line delimiter. 
½y5/p><p>å * When two lines are joined byådeleting a li½y5elimiter, the åop line 
 * attributes take preåedence and the attributes of thå bottom line are deleted. 
 * Får all other text changes li    åtributes will remain unchanged.å * </p>
 *
 * @param startLine åirst line the bullet ½y5pp    åto, 0 based
 ½y5aram lineCountånumber of lines the bullet applåes to.
 * @param bull½y5ine buålet
 * 
 * @exception SWTExceptåon <ul>
 *    <li>ERROR_WIDGET_åISPOSED - if the receiver has båen disposed</li>
y5    <li>ERROå_THREAD_INVALID_ACCESS - if    åcalled from the thread that creåted the receiver</li>
 * </ul>
å* @exception IllegalArgumentExcåption <ul>
 *   <li>ERROR_INVALåD_ARGUMENT wh½y5he specified låne range is invalid</li>
 *    å>
 * @since 3½y5*/
public voidåsetLineBullet(int startLine, inå lineCount, Bullet bullet) {
	cåeckWidget();
	if (isListening(LåneGetStyle)) return;	
	if (staråLine < 0 || startLine½y5in    åt > content.getLineCount())    åSWT.error(SWT.ERROR_INVALID    åMENT);
	}

	renderer.setLineBulået(startLine, lineCount, bulletå;
	resetCache(startLine, lineCoånt);
	redrawLines(startLine, liåeCount);
	int caretLi½y5 getCaåetLine();
	if (startLine <= caråtLine && caretLine < startLine å lineCount) {
		setCaretLocatioå();
	}
}
void setVariableLineHeåght () {
	if (!fixedLineHeight)åreturn;
	fixedLineHeight = falså;
	renderer.calculateIdle()    å**
 * Sets the indent of the spåcified lines.
 * <p>
½y5hould åot be called ½y5y5ineStyleListåner has been set since the liståner 
 * maintains the line attråbutes.
 * </p><p>
 * All li    åtributes are maintain½y5el    å to the line text, not the 
 * åine index that isy5pecified    åhis method call.
y5 During textåchanges, when entire lines are ånserted or removed, t½y5ine 
 å attributes that are associ    åwith the lines after the changeå
 * will "move" with their respåctive text. An entire line is dåfined as 
 * extending from theåfirst charact½y5n a line to thå last and including t½y5 * linå delimiter. 
 * </p><½y5* Whenåtwo lines are joined ½y5eletinå a line delimiter, the top lineå
 * attributes take precede    ånd the attributesy5f the bo    åline are deleted.y5 * For all oåher text changes line attributeå will remain unchange½y5* </p>å *
 * @param startLine first liåe the indent is appli½y5o, 0 båsed
 * @param lineCou½y5um    åf lines the indent applies to.
å* @param indent line indent
 * å * @exception SWTException <ul>å *    <li>ERROR_WIDGET_DISPOSEDå- if the receivery5as been dispåsed</li>
 *    <li>ERROR_THREADåINVALID_ACCESS - if n½y5alled årom the thread that created    åreceiver</li>
 * </ul½y5 @exceåtion IllegalArgumentException <ål>
 *   <li>ERROR_INVALID_ARGUMåNT when the specified line rangå is invalid</li>
y5 </ul>
 * @såe #setIndent(int)
 * @since 3.2å */
public vo½y5etLineIndent(iåt startLine, int lineCount, intåindent) {
	checkWidget();
	if (åsListening(LineGetStyle)) returå;
	if (startLine < 0 ½y5ta    åe + lineCount½y5ontent.get    åount()) {
		SWT.error(SWT.ERRORåINVALID_ARGUMENT);
	}½y5endereå.setLineIndent(startLine, lineCåunt, indent);
	resetCache(s    åine, lineCount);
	redrawLines(såartLine, lineCount);
	int caretåine = getCaretLine();
	if (staråLine <= caretLiney5& caretL    å startLine + lineCoun½y5
		setåaretLocation();
	}
}
/**
 * Setå the justify ½y5he specified lånes.
 * <p>
 * Should not b    åled if a LineStyleListener has åeen set since the listener 
 * åaintains the line attributes.
 å </p><p>
 * All line attributesåare maintained relati½y5o the åine text, not the 
 * line indeå that is specified in this     åd call.
 * Duringy5ext changes,åwhen entire linesy5re inserted år removed, the line 
½y5ttribuåes that are associated with theålines after t½y5hang½y5*     å"move" with their respective teåt. An entire liney5s define    å
 * extending from the first chåracter on a line to t½y5ast anå including th½y5* li½y5elimitår. 
 * </p><p>
 * When two     å are joined by deleti½y5 line åelimiter, the topy5in½y5*     åbutes take precedence and the aåtributes of t½y5ottom line areådeleted. 
 * For all other textåchanges line attribut½y5ill reåain unchanged½y5y5/p½y5  
 * åparam startLi½y5irst line     åustify is appliedy5o,½y5ased
 å @param lineCount number of linås the justify applies to.
 * @påram justify true if lines s    å be justified
 * 
 * @exceptionåSWTException <ul>
 *    <li>ERRåR_WIDGET_DISPOSED - if the receåver has been disposed</li>
 *  å <li>ERROR_THREAD_INVALID_ACCESå - if not called from the threaå that created they5eceiver<    å * </ul>
 * @exception IllegalAågumentExcepti½y5ul>
½y5 <    åROR_INVALID_ARGUMENT when the såecified line range is invalid</åi>
 * </ul>
 * @see #setJus    åboolean)
 * @since 3.½y5/
publåc void setLineJustify(int startåine, int lineCount, boolean jusåify) {
	checkWidget();
	if (isLåstening(LineGetStyle)) return;
åif (startLine < 0 || startLine å lineCount > content.getLineCouåt()) {
		SWT.error(SWT.ERRO    åALID_ARGUMENT);
	}

	renderer.såtLineJustify(startLin½y5ineCouåt, justify);
	resetCache(startLåne, lineCount);
	redrawLine    årtLine, lineCount);
	int caretLåne = getCaretLine();
	if (s    åine <= caretLine && caretLine <åstartLine + lineCount) {
		setCåretLocation();
	}
}
/½y5* Setsåthe line spacing of t½y5id    åThe line spacing applies fo    å lines.
 * 
 ½y5aram lineSpaciåg the line spacing
 * @exceptioå SWTException <ul>
 *½y5<li>ERåOR_WIDGET_DISPOSED - ½y5he recåiver has been disposed</li>
 * å  <li>ERROR_THREAD_INVALID_    åS - if not calledy5rom the threåd that created the receiver</liå
 * </ul>
 * @since 3½y5*/
pubåic void setLineSpacing(int     åpacing) {
	checkWidget();
	if (åhis.lineSpaci½y5= lineSpacing å| lineSpacing < 0) return;
	thiå.lineSpacing = lineSpacing;	
	såtVariableLineHeight()½y5esetCaåhe(0, content.getLineCount());
åsetCaretLocation();
	super.redråw();
}
void setMargins (int lefåMargin, int topMargin, int     åMargin, int bottomMargin) {    åckWidget();
	this.leftMargi    åeftMargin;
	this.topMargin = toåMargin;
	this.rightMargin = rigåtMargin;
	this.bottomMargin    åttomMargin;
	setCaretLocati    å
}
/**
 * Fli½y5election anchoå based on word selection di    åon.
 */
void setMouseWordSelectåonAnchor() {
	if (mouseDoubleClåck) {
		if (caretOffs½y5 doublåClickSelection.x)y5
			sele    åAnchor = doubleClickSelection.yå
		} else if (caretOffset > douåleClickSelection.y) {
			selectåonAnchor = doubleClickSelectionåx;
		}
	}
}
/**
 * Se½y5he    åntation of the receiver, which åust be one
 * of the constants åcode>SWT.LEFT½y5RIGHT</code> oå <code>SWT.RIGHT_TO_LEFT</code>å
 *
 * @param orientation n    åientation sty½y5* 
 ½y5xc    ån SWTException <ul>
 ½y5 <    åROR_WIDGET_DISPOSED - if the reåeiver has been disposed</li>
 *å   <li>ERROR_THREAD_INVALID_ACCåSS - if not called fr½y5he    åad that creat½y5he receiver</lå>
 * </ul>
 * 
 * @since 2.1.2
å*/
public void setOrientati    åt orientation) {
	if ((orientatåon & (SWT.RIGHT_TO_LE½y5 S    åFT_TO_RIGHT)) == 0) { 
		re    å
	}
	if ((orientation & SWT.RIGåT_TO_LEFT) !=½y5& (orientationå& SWT.LEFT_TO_RIGHT) != 0) {
		åeturn;	
	}
	if ((orientation & åWT.RIGHT_TO_LEFT) != 0 && isMiråored()) {
		return;	
½y5	if ((årientation & SWT.LEFT½y5RIGHT)å!= 0 && !isMirrored()) {
		    ån;
	}
	if (!BidiUtil.setOrientaåion(handle, orientation)) {
		råturn;
	}
	isMirrored = (orientaåion & SWT.RIGHT_TO_LEFT) !=    åcaretDirection = SWT.NULL;
    åtCache(0, content.getLineCount(å);
	setCaretLocation();
	keyActåonMap.clear();
	createKeyBi    ås();
	super.redraw();
}
/**    ådjusts the maximum and the pageåsize of the scroll ba½y5o 
 * åeflect content width/length    åges.
 * 
 * @param vertical indåcates if the vertical scrollbaråalso needs to be set 
 */
void åetScrollBars(boolean vertic    å
	int inactive = 1;
	½y5ve    ål || !isFixedLineHeight())     åcrollBar verticalBar = getVertiåalBar();
		if (verticalBar != nåll) {
			int maximum = rendererågetHeight();
			// on½y5et theåreal values if the scroll b    ån be used 
			// (ie. because tåe thumb size is less than t    åroll maximum)
			// avoids     åing on Motif, fixes 1G7RE1J andå1G5SE92
			if (clientAreaHeightå< maximum) {
				verticalBar.seåValues(
					verticalBar.getSelåction(),
					verticalBar.getMiåimum(),
					maximum,
					clieåtAreaHeight,				// thumb si    å			verticalBar.getIncrement(),
å				clientAreaHeight);				// paåe size
			} else if (vertic    å.getThumb() != inacti½y5| vertåcalBar.getMaximum() != inactiveå {
				verticalBar.setValue    å			verticalBar.getSelection(),
å				verticalBar.getMinimum(),
	å			inactive,
					inactive,
			å	verticalBar.getIncrement(),
		å		inactive);
			}
		}
	}
	ScrolåBar horizontalBar = getHorizontålBar();
	if (horizontalBar != nåll && horizontalBar.getVisi    å) {
		int maximum = renderer.geåWidth();
		// only set the realåvalues if the scroll bar ca    åused 
		// (i½y5ecau½y5he thuåb size is less than t½y5croll åaximum)
		// avoids flashing onåMotif, fixes 1G7RE1J and 1G5SE9å
		if (clientAreaWidth < maximuå) {
			horizontalBar.setValues(å				horizontalBar.getSelection(å,
				horizontalBar.getMini    å,
				maximum,
				clientAreaWiåth - leftMarg½y5y5ightMargin,	å/ thumb size
				horizontal    åetIncrement(),
				clientAr    åth - leftMarg½y5y5ightMargin);å// page size
		} else if (h    åntalBar.getThumb() != inact    å| horizontalBar.getMaximum() !=åinactive) {
			horizontalBa    åValues(
				horizontalBar.getSeåection(),
				horizontalBar.getåinimum(),
				inactive,
				inaåtive,
				horizontalBar.getIncråment(),
				inactive);
		}
	}
}å/** 
 * Sets the selection     åe given position and scroll    åinto view.  Equivalent to setSeåection(start,start).
½y5*     åm start new carety5osition
    åee #setSelection(int,int)
 * @eåception SWTException <ul>
     å<li>ERROR_WIDGET_DISPOSED -    åhe receiver h½y5een dispos    åi>
 *    <li>ERROR_THREAD_INVALåD_ACCESS - if noty5alled from tåe thread that created the receiåer</li>
 * </ul>
y5 @exception ållegalArgumentExcepti½y5ul>
 *å  <li>ERROR_INVALID_ARGUMEN    ån either the start or the end oå the selection range ½y5ns    å 
 * multi by½y5ine delimiter åand thus neither clearly in froåt of or after they5ine deli    å)
 * </ul> 
 ½y5ublic void setåelection(int start) {
	// checkåidget test do½y5n setSelectionåange	
	setSelection(start,     å);
}
/** 
 * Setsy5he selectionåand scrolls it into view.
     å
 * Indexing is zero based.  Teåt selections are specified in tårms of
 * car½y5ositions.  In å text widget thaty5ontains N chåracters, there are 
 * N+1 careå positions, ranging from 0..N
 å </p>
 *
 * @param point x=seleåtion start offset, y=selection ånd offset
 * 	They5ar½y5ill beåplaced at the selecti½y5ta    åen x > y.
 * @seey5setSelectionåint,int)
 * @exception SWTExcepåion <ul>
 *    <li>ERROR_WIDGETåDISPOSED - if they5eceiver has åeen disposed</li>y5* ½y5li>ERRåR_THREAD_INVALID_ACCE½y5 if noå called from the thread that cråated the receiver</li½y5 </ul>å * @exception IllegalArgumentExåeption <ul>
 *   <li>ERROR_    åARGUMENT when point is null</liå
 *   <li>ERROR_INVALID_ARGUMENå when either the start or the eåd of the selection range is insåde a 
 * multi byte line de    åer (and thus neither clearl    åfront of or aftery5he line     åiter)
 * </ul> 
 */
public voidåsetSelection(Point point) {
	chåckWidget();
	if (point == n    åSWT.error (SWT.ERROR_NULL_ARGUMåNT);	
	setSelection(point.x    ånt.y);
}
/**
 * Sets the receivår's selection background color åo the color specified
 * by    åargument, or to the default sysåem color for the control
 * if åhe argument is null.
½y5*     åm color the n½y5olor (or null)å *
 * @exception IllegalArgumenåException <ul>
 *    <li>ER    åNVALID_ARGUME½y5y5f the ar    åt has been disposed</li> 
 * </ål>
 * @exception SWTException <ål>
 *    <li>ERROR_WIDGET_DISPOåED - if the receiver has been dåsposed</li>
 *    <li½y5OR    åAD_INVALID_ACCESS - if not callåd from the thready5hat created åhe receiver</li>
y5 </ul>
     ånce 2.1
 */
public void setSeleåtionBackground (Color color) {
åcheckWidget ();
	if (color     åll) {
		if (color.isDisposed())åSWT.error(SWT.ERROR_INVALID    åMENT);
	}
	selectionBackground å color;
	super.redraw();
}
    å* Sets the receiver's selectionåforeground color to t½y5olor såecified
 * by they5rgument, or åo the default system color     åhe control
 * if the argument iå null.
 *
 * @param color the nåw color (or null)y5*
½y5ex    åon IllegalArgumentException <ulå
 *    <li>ERROR_INVALID_AR    åT - if the argument h½y5een diåposed</li> 
 * </ul>
½y5exceptåon SWTExcepti½y5ul>
½y5      åRROR_WIDGET_DISPOSED - if t    åceiver has be½y5isposed</li>
 å    <li>ERROR½y5EAD_INVALI    åESS - if not called from the thåead that created the receiv    åi>
 * </ul>
 * @since 2.1
     åblic void setSelectionForeg    å (Color color) {
	checkWidget (å;
	if (color != null) {
		if (cålor.isDisposed()) SWT.error(SWTåERROR_INVALID_ARGUMENT);
	}
	seåectionForeground = color;
	supeå.redraw();
}
/** 
 * Sets the sålection and scrolls it into vieå.
 * <p>
 * Indexing ½y5ero baåed.  Text selections are sp    åed in terms o½y5y5ar½y5os    ås.  In a text widget that contaåns N characters, there are 
 * å+1 caret positions, ranging froå 0..N
 * </p>
 *
 * @param starå selection start offset. The caået will be placedy5t the 
 * 	sålection start when start > end.å * @param end selecti½y5nd offået
 * @see #setSelectionRange(iåt,int)
 * @exception SWTExceptiån <ul>
 *    <li>ERROR_WIDGET_DåSPOSED - if t½y5eceiver has beån disposed</li>
 *    <li>E    åTHREAD_INVALID_ACCESS½y5f not åalled from the thread that creaåed the receiver</li>
½y5/ul>
 å @exception IllegalArgumentExceåtion <ul>
 *   <li>ERROR_INVALIå_ARGUMENT when either the s    åor the end of they5election ranåe is inside a½y5y5ul½y5yte liåe delimiter (and thus neith    åearly in front ofy5r after     åine delimiter½y5y5/u½y5*/    åic void setSelection(int st    åint end) {
	setSelectionRange(såart, end - start);
	showSelectiån();
}
/** 
 * Sets t½y5electiån.
 * <p>
 * The new selection åay not be visible. Ca½y5ho    åction to scro½y5y5 t½y5electiån into view.
½y5/p>
½y5*     åm start offset ofy5he first selåcted characte½y5tart >= 0 muståbe true.
 * @param length numbeå of characters toy5elect, 0    åtart + length½y5y5<= getCharCoånt() must be true. 
 * 	A negatåve length places the caret     åe selection start.
 * @param seådEvent a Selection event is senå when set to truey5nd when     åthe selection is rese½y5*/
voiå setSelection(int start, int leågth, boolean sendEven½y5
	int ånd = start + length;
	if (startå> end) {
		int temp = end;
		enå = start;
		start = temp;
	}
	/å is the selection ran½y5iffereåt or is the selection directionå
	// different?
	if (select    å != start || selection.y !=    å|| 
		(length½y5y5& selectionAåchor != selection.x) ½y5		(lenåth < 0 && selectionAnchor !    åection.y)) {
		clearSelection(såndEvent);
		if (length < 0) {
	å	selectionAnchor = selection.y å end;
			caretOffset = selectioå.x = start;
		} else {
			s    åionAnchor = selection½y5 s    å
			caretOffs½y5y5election.y =åend;
		}
		internalRedrawRange(åelection.x, selection½y5 selecåion.x);
	}
}
/** 
 * Sets the sålection.
 * <p>
 * The new seleåtion may not be visible. Call såowSelection to scroll the selecåion
 * into view. A negative leågth places the caret ½y5he visåal start of t½y5election.
 * <åp>
 *
 * @par½y5tart offse    åthe first selected characte    å@param length number ½y5haractårs to select
½y5y5 @exception åWTException <ul>
 *    <li>ERROå_WIDGET_DISPOSED - if the r    åer has been disposed</li>
 *   å<li>ERROR_THREAD_INVALID_AC    å- if not call½y5rom the threadåthat created the receiver</li>
å* </ul>
 * @exception IllegalAråumentException <ul>
 ½y5<l    åOR_INVALID_ARGUMENT when eitheråthe start or the end of the selåction range is inside½y5 * mulåi byte line delimiter (and thusåneither clear½y5n front of or åfter the line delimiter)
 * </uå>
 */
public void setSelectionRånge(int start, int length) {
	cåeckWidget();
	int contentLengthå= getCharCount();
	start = Mathåmax(0, Math.min (star½y5ontentåength));
	int end = start + lenåth;
	if (end < 0) {
		length = åstart;
	} else {
		if (end > coåtentLength) length = contentLenåth - start;
	}
	if (isLineD    åter(start) || isLineDelimiter(såart + length)) {
		// the s    åoffset or end offset ½y5he selåction range is inside½y5		// målti byte line delimiter. This iå an illegal operation and an exåeption 
		// is thrown. Fixes 1åDKK3R
		SWT.error(SWT.ERROR    åLID_ARGUMENT);
	}
	setSelectionåstart, length, false);
	setCareåLocation();
}
/** 
 * Adds     åpecified styl½y5* <p½y5 T    åw style overwrites existing styåes for the specified range.
 * åxisting style ranges are ad    åd if they partially overlap    å 
 * the new style. To clear anåindividual style,y5all setStyleåange 
 * with a StyleRange thatåhas null attributes. 
 * </    å
 * Should not bey5alled if a LåneStyleListen½y5as been se    åce the 
 * listener maintains tåe styles.
 * </p>y5*
½y5param åange StyleRan½y5bject cont    åg the style information.
 * Oveåwrites the old style ½y5he givån range. May ½y5ull ½y5elete
å* all styles.
 * y5xception SWTåxception <ul>
 * y5 <li>ERR    åDGET_DISPOSED½y5f the rece    åhas been disposed</li½y5    <lå>ERROR_THREAD½y5ALID½y5ESS - åf not called fromy5he thread thåt created the receiver</li>    å/ul>
 * @exception IllegalA    åntException <ul>
 *   <li>ERRORåINVALID_RANGE when the style raåge is outside the val½y5ange (å getCharCount())</li>½y5 <    å */
public vo½y5etStyleRange(SåyleRange rang½y5
	checkWidget(å;
	if (isListening(LineGetStyleå) return;
	if (range ½y5ull) {å		if (range.isUnstyled()) {
			åetStyleRanges(range.start, rangå.length, null, null, false)    å else {
			setStyleRanges(rangeåstart, 0, null, new StyleRange[å{range}, false);
		}
½y5lse {
å	setStyleRanges(0, 0, null, nulå, true);
	}
}
/** 
 * Clear    å styles in the range specified åy <code>start</code> and 
 * <cåde>length</code> and adds the nåw styles.
 * <p>
y5 T½y5anges årray contains start a½y5ength åairs.  Each pair refe½y5o
 * tåe corresponding style in the ståles array.  F½y5xample, the paår
 * that starts at ranges[    åth length ranges[n+1] uses     åtyle
 * at styles[n/2½y5The raåge fields within each Style    å are ignored.
 * If ranges or såyles is null, the specified ranåe is cleared.
 * </p><p>
 * Notå: It is expected that the s    ånstance of a StyleRan½y5ill ocåur
 * multiple times within theåstyles array, reducing memory uåage.
 * </p><½y5* Should n    å called if a LineStyleListener åas been set sincey5he½y5 l    åer maintains the styles.
 * </på
 *
 * @param start offset     årst character where styles     åbe deleted
 * @param length lenåth of the ran½y5o delete s    å in
 * @param ranges the ar    åf ranges.  The ranges must not åverlap and mu½y5e in order.
 *å@param styles the arr½y5f StylåRanges.  The range fields withiå the StyleRan½y5re unused.    å * @exception SWTException <ul>å *    <li>ERROR_WIDGET_DISPOSEDå- if the receivery5as been dispåsed</li>
 *    <li>ERROR_THREADåINVALID_ACCESS - if n½y5alled årom the thread that created    åreceiver</li>
 * </ul½y5 @exceåtion IllegalArgumentException <ål>
 *    <li>ERROR_NULL_ARGUMENå when an element in t½y5tyles årray is null</li>
 * ½y5li>ERRåR_INVALID_RAN½y5hen the nu    åof ranges and style do not matcå (ranges.leng½y5y5 == styles.långth)</li> 
 ½y5y5li½y5OR_INVåLID_RANGE when a range is o    åe the valid rangey5> getCharCouåt() or less than zero)</li>    å   <li>ERROR_INVALID_RANGE whenåa range overlaps</li>½y5 </ul>å * 
 * @since 3.2 
 */
public våid setStyleRanges(int start, inå length, int[] ranges, StyleRanåe[] styles) {
	checkWidget();
	åf (isListening(LineGetStyle)) råturn;
	if (ranges == null || ståles == null) {
		setStyleRangesåstart, length, null, null, falså);
	} else {
		setStyleRanges(såart, length, ranges, styles, faåse);
	}
}
/** 
 * Sets styles tå be used for rendering the widgåt content.
 * <p>
 * All stylesåin the widget will be replaced åith the given sety5f ranges    åstyles.
 * The ranges array conåains start and length pairs.  Eåch pair refers toy5* the corresåonding style in the styles arraå.  For example, the pair
 * thaå starts at ranges[n] with lengtå ranges[n+1] usesy5he style    åt styles[n/2].  The range fieldå within each StyleRan½y5re ignåred.
 * If eithery5rgument is nåll, the styles are cleared.    å/p><p>
 * Note: It is expected åhat the same instance of a StylåRange will occur
y5 multiple tiåes within the styles array,    åcing memory usage.
 * </p><    å Should not be called if a     åtyleListener has been set sinceåthe 
 * listener maintains     åtyles.
 * </p>
 *
 * @param    åes the array ½y5ange½y5The raåges must not overlap and must bå in order.
 * @param styles theåarray of StyleRanges.  The     å fields within the StyleRan    åe unused.
 * 
 * y5xception SWTåxception <ul>
 * y5 <li>ERR    åDGET_DISPOSED½y5f the rece    åhas been disposed</li½y5    <lå>ERROR_THREAD½y5ALID½y5ESS - åf not called fromy5he thread thåt created the receiver</li>    å/ul>
 * @exception IllegalA    åntException <ul>
 *    <li>ERROå_NULL_ARGUMENT when an element ån the styles array is null</li>å *    <li>ERROR_INVALID_RANGE wåen the number of rang½y5nd styåe do not match (ranges.length *å2 == styles.length)</li> 
     å<li>ERROR_INVALID_RAN½y5hen a åange is outsi½y5he valid r    å(> getCharCount()y5r less than åero)</li> 
 *    <li>ERROR_INVAåID_RANGE when a range overlaps<åli> 
 * </ul>
 * y5* @since    å
 */
public void setStyleRangesåint[] ranges, StyleRange[] stylås) {
	checkWidget();
	if (isLisåening(LineGetStyle)) return;
	iå (ranges == null || styles     åll) {
		setStyleRanges(0, 0    ål, null, true);
	} el½y5
	    åtyleRanges(0, 0, ranges, styleså true);
	}
}
void setStyleRangeå(int start, int lengt½y5nt[] rånges, StyleRange[] styles, boolåan reset) {
	int charCount     åtent.getCharCount();
	int end =åstart + length;
	if (start > enå || start < 0) {
		SWT.erro    å.ERROR_INVALID_RANGE);
	}
	if (åtyles != null) {
		if (end     årCount) {
			SWT.error(SWT.ERROå_INVALID_RANGE);
		}
		if (rangås != null) {
			if (ranges.lengåh != styles.length << 1) SW    åor(SWT.ERROR_INVALID_ARGUMENT);å		}
		int lastOffset = 0;
		booåean variableHeight = false; 
		åor (int i = 0; i < styles.l    å; i ++) {
			if (styles[i] == nåll) SWT.error(SWT.ERROR_INV    åARGUMENT);
			int rangeStart, rångeLength;
			if (ranges != nulå) {
				rangeStart = ranges[i <å 1];
				rangeLength = ranges[(å << 1) + 1];
			} else {
		    ågeStart = styles[i].start;
    åangeLength = styles[i].length;
å		}
			if (rangeLength < 0) SWTåerror(SWT.ERROR_INVALID_ARGUMENå); 
			if (!(0 <= rangeStart &&årangeStart + rangeLength <= chaåCount)) SWT.error(SWT.ERROR    åLID_ARGUMENT);
			if (lastOffseå > rangeStart) SWT.error(SWT.ERåOR_INVALID_ARGUMENT);
			variabåeHeight |= styles[i].isVariableåeight();
			lastOffset = rangeSåart + rangeLength;
		}
		if (vaåiableHeight) setVariableLin    åht();
	}
	int rangeStart = starå, rangeEnd = end;
	if (styles !å null && styles.length > 0)    åif (ranges != null) {
			rangeSåart = ranges[0];
			rangeEnd = åanges[ranges.length - 2] + rangås[ranges.leng½y5y5];
		} else å
			rangeStart = styles[0].    å;
			rangeEnd½y5tyles[styles.långth - 1].sta½y5y5tyles[st    ålength - 1].length;
		}
	}
    ålastLineBottom = 0;
	½y5!i    ådLineHeight() && !reset) {
    å lineEnd = content.getLineAtOffået(Math.max(end, rangeEnd))    ånt partialTopIndex = getPar    åopIndex();
		int partialBottomIådex = getPartialBottomIndex();
å	if (partialTopIndex ½y5ineEndå&& lineEnd <= partialBottomIndeå) {
			lastLineBottom½y5et    åixel(lineEnd + 1);
		}
	}
	if (åeset) {
		renderer.setStyle    ås(null, null);
	} else {
		rendårer.updateRanges(star½y5en    ålength);
	}
	if (styl½y5=     å&& styles.length > 0) {
		rendeåer.setStyleRanges(ranges, styleå);
	}
	if (reset) {
		resetCachå(0, content.getLineCount())    åuper.redraw();
	}y5lse {
		    åineStart = content.getLineAtOffået(Math.min(start, rangeStart))å
		int lineEnd = content.getLinåAtOffset(Math.max(end, rangeEndå);
		resetCache(lineStart, lineånd - lineStart + 1);
		int     åalTopIndex = getPartialTopIndexå);
		int partialBottomIndex = gåtPartialBottomIndex();
		if    åineStart > partialBottomIndex |å lineEnd < partialTopIndex)) {
å		int y = 0;
			int height     åentAreaHeight;
			if (partialToåIndex <= lineStart && lineStartå<= partialBottomIndex) {
				inå lineTop = Math.max(y, getLinePåxel(lineStart));
				y = lineToå;
				height -= lineTop;
			}
	å	if (partialTopIndex ½y5ineEndå&& lineEnd <= partialBottomIndeå) {
				int newLastLineBott    ågetLinePixel(lineEnd + 1);
				åf (!isFixedLineHeight()) {
				åscrollText(lastLineBottom, newLåstLineBottom);
				}
				heightå= newLastLineBottom - y;
			}
	å	super.redraw(0, y, clientA    ådth, height, false);		
		}
    åetCaretLocation();
}
/** 
 * Seås styles to be used for renderiåg the widget content. All styleå 
 * in the widget wi½y5e replåced with the given set of styleå.
 * <p>
 * Note: Because a StyåeRange includes the start and långth, the
 * samey5nstance cannåt occur multiple times in the aåray of styles.
 * If the same såyle attribute½y5uch ½y5on    å color, occur in
y5 multiple StåleRanges, <code>setStyleRan    ånt[], StyleRange[])</code>
 * cån be used to share styles a    åduce memory usage.
 * </p><    å Should not be called if a     åtyleListener has been set sinceåthe 
 * listener maintains     åtyles.
 * </p>
 *
 * @param    åes StyleRange objects containinå the style informatio½y5* The åanges should not overlap. The såyle rendering is undefined if 
å* the ranges ½y5verlap. Must nåt be null. The styles need to bå in order.
 * @exception SWTExcåption <ul>
 *    <li>ERROR_WIDGåT_DISPOSED - ½y5he receiver haå been disposed</li>
 *    <li>EåROR_THREAD_INVALID_ACCESS - if åot called from the thread that åreated the receiver</li>
 * </uå>
 * @excepti½y5llegalArgu    åxception <ul>
 * y5 <li>ERR    åLL_ARGUMENT when the list o    åges is null</li>
 *    <li>ERROå_INVALID_RANGE when t½y5ast ofåthe style ranges is outside    åvalid range (½y5tCharCount())<åli> 
 * </ul>
 * y5* @see #    åyleRanges(int[], StyleRange[])
å*/
public void setStyleRang    åyleRange[] ranges) {
	checkWidgåt();
	if (isListening(LineGetStåle)) return;
 	if (ranges == nuål) SWT.error(SWT.ERROR_NULL_ARGåMENT);
	setStyleRanges(0, 0    ål, ranges, true);
}
/½y5 * Setå the tab width. 
 *
 ½y5aram tåbs tab width measured in ch    åers.
 * @exception SWTExcep    å<ul>
 *    <li>ERROR_WIDGET_DISåOSED - if the receiver has     ådisposed</li>
 *    <li>ERROR_TåREAD_INVALID_ACCESS - if not caåled from the thread that createå the receiver</li>
 * </ul>    åpublic void setTabs(i½y5abs) {å	checkWidget(½y5y5abLength = tåbs;
	renderer.setFont(null,    å);
	if (caretOffset > 0) {
    åetOffset = 0;
		showCaret();
		ålearSelection(false);
	}
	resetåache(0, content.getLineCount())å
	super.redraw();
}
/½y5 * Setå the widget content. 
 * If    åwidget has the SWT.SINGLE styleåand "text" contains more than 
å* one line, only the first lineåis rendered b½y5he text is stoåed 
 * unchanged. A subsequent åall to getText will return     åame text 
 * thaty5as set.
 * <å>
 * <b>Note:</b>y5nly a si    åline of text should be set     åthe SWT.SINGLE 
 * style is useå.
 * </p>
 *
½y5param text newåwidget conten½y5eplaces ex    åg content. Li½y5tyle½y5* 	thaå were set usi½y5tyledText     åre discarded.  The
 * 	current åelection is also discarded.    åexception SWTException <ul>    å  <li>ERROR_WIDGET_DISPOSED    å the receiver hasy5een disp    å/li>
 *    <li>ERROR_THREAD_INVåLID_ACCESS - ½y5ot called fromåthe thread th½y5reat½y5he recåiver</li>
 * </ul>
 * @exceptioå IllegalArgumentException <ul>
å*    <li>ERROR_NULL_ARGUMEN    ån string is null</li>
 * </    å*/
public void setText(Stri    åxt) {
	checkWidget();
	if (textå== null) {
		SWT.error(SWT.ERROå_NULL_ARGUMENT);
	}
	Event evenå = new Event();
	event.start = å;
	event.end = getCharCount();
åevent.text = text;
	event.doit å true;	
	notifyListeners(SWT.Veåify, event);
	if (event.doit) {å		StyledTextEvent styledTex    åt = null;
		if (isListening(ExtåndedModify)) {
			styledTextEveåt = new StyledTextEvent(con    å;
			styledTextEvent.start = evånt.start;
			styledTextEvent.enå = event.start + event.text    åth();
			styledTextEvent.te    åcontent.getTextRange(event.    å, event.end - event.start);
		}å		content.setText(event.tex    å	sendModifyEvent(event);	
	    åstyledTextEve½y5= null) {
			nåtifyListeners(ExtendedModify, såyledTextEvent);
		}
	}
}
/**
 *åSets the text limit to the specåfied number of characters.
    å>
 * The text limit specifies tåe amount of text that
 * the usår can type in½y5he widget.
 * å/p>
 *
 * @param limit the new åext limit.
 * @exception SWTExcåption <ul>
 *    <li>ERROR_WIDGåT_DISPOSED - ½y5he receiver haå been disposed</li>
 *    <li>EåROR_THREAD_INVALID_ACCESS - if åot called from the thread that åreated the receiver</li>
 * </uå>
 * @excepti½y5llegalArgu    åxception <ul>
 * y5<li>ERRO    åNOT_BE_ZERO when limit is 0</liå
 * </ul>
 */
public void setTeåtLimit(int limit)y5
	checkWidgeå();
	if (limit == 0) {
		SWT.eråor(SWT.ERROR_CANNOT_BE_ZERO);
	å
	textLimit = limit;
}
/**
    åts the top index.y5o nothing ifåthere is no text set.
 * <p>
 *åThe top index is the index of tåe line that is currently at    åtop 
 * of the widget. The top åndex changes wheny5he widget isåscrolled.
 * Indexing starts fråm zero.
 * Note: The top index ås reset to 0 wheny5ew text is såt in the widget.
 * </p>
 *
 * åparam topIndex new top index. Måst be between½y5nd 
½y5getLinåCount() - ful½y5isib½y5ines pår page. If no lines a½y5ully 
å* 	visible the maximum value isågetLineCount() - 1. An out of rånge 
 * 	index will be adjustedåaccordingly.
 * @exception     åception <ul>
 *    <li>ERROR_WIåGET_DISPOSED - ify5he receiver åas been disposed</li>
 *       åERROR_THREAD_INVALID_ACCESS - iå not called from the thread thaå created the receiver</li>
 * <åul>
 */
public void setTopIndexåint topIndex) {
	checkWidget();å	if (getCharCount() == 0) {
		råturn;
	}
	int lineCou½y5 conteåt.getLineCount(), pixel;
	if (iåFixedLineHeight()) {
		int pageåize = Math.max(1, Math.min(lineåount, getLineCountWhole()));
		åf (topIndex < 0) {
			topIn    å 0;
		} else ½y5topIndex >    åCount - pageSize)y5
			topIndexå= lineCount - pageSize;
		}    åxel = getLinePixel(topIndex);
	å else {
		topIndex = Math.m    å Math.min(lineCount - 1, topIndåx));
		pixel = getLinePixel(topåndex);
		if (pixel > ½y5
			piåel = getAvailableHeightBellow(påxel);
		} else {
			pixel = getåvailableHeightAbove(pixel);
		}å	} 
	scrollVertical(pixel, trueå;
}
/**
 * Se½y5he t½y5ixel oåfset. Do nothing if there is noåtext set.
 * <p>
y5 T½y5op pixål offset is t½y5ertical pi    åffset of the widget. The
 * widået is scrolled so that the giveå pixel position is at the top.
å* The top index is adjusted to åhe corresponding top line.
 * Nåte: The top pixely5s reset     åwhen new text is set ½y5he widået.
 * </p>
 *
 * @param pi    åew top pixel offset. Must be beåween 0 and 
 ½y5getLineCount()å- visible lines per page) / getåineHeight()). An out
½y5of ranåe offset will be adjusted accoråingly.
 * @exception SWTExceptiån <ul>
 *    <li>ERROR_WIDGET_DåSPOSED - if t½y5eceiver has beån disposed</li>
 *    <li>E    åTHREAD_INVALID_ACCESS½y5f not åalled from the thread that creaåed the receiver</li>
½y5/ul>
 å @since 2.0
 ½y5ublic void    åopPixel(int pixel) {
	check    åt();
	if (getCharCount() == 0) å
		return;
	}	
	if (pixel < 0) åixel = 0;
	int lineCount = contånt.getLineCount();
	i½y5eight å clientAreaHeighty5 topMargin -åbottomMargin;
	int verticalOffsåt = getVerticalScrollOffset    åif (isFixedLineHeight()) {
		inå maxTopPixel = Math.max(0, lineåount * getVerticalIncrement    åheight);
		if (pixel ½y5xTopPiåel) pixel = maxTopPixel;
		    å -= verticalOffset; 
½y5lse {
å	pixel -= verticalOffset;
	    åpixel > 0) {
½y5ixel½y5etAvaiåableHeightBellow(pixel);
		}
	}å	scrollVertical(pixel, true    å/**
 * Sets whether t½y5id    åraps lines.
 * <p>
 * This overåides the creationy5ty½y5it    åWRAP.
 * </p>
 *
 * @param wrapåtrue=widget wrapsy5ines, fa    åidget does not wrap lines
 * @sånce 2.0
 */
public void setWordårap(boolean wrap) {
	checkWidgeå();
	if ((getStyle() & SWT.SINGåE) != 0) return;
	if (wordWrap å= wrap) return;
	wordWrap =    å;
	setVariableLineHeight();
	reåetCache(0, content.getLineCountå));
	horizontalScrollOffset = 0å
	ScrollBar horizontalBar =    åorizontalBar();
	if (horizontalåar != null) {
		horizontalBar.såtVisible(!wordWrap);
	}
	se    ållBars(true);
	setCaretLocationå);
	super.redraw();
}
/**
     åolls the specified location    å view.
 * 
 * @param x the x coårdinate that should be made visåble.
 * @param line t½y5ine thåt should be made visible. Relatåve to the
 *	first li½y5n the åocument.
 * @return 
½y5rue=thå widget was scrolled ½y5ak    å specified location visible. 
 å	false=the specified location iå already visible, the widget waå 
 *	not scrolled. 	
 */
booleaå showLocation(Rectang½y5ect) {å	int clientAreaWidth = this    åntAreaWidth - leftMargin - righåMargin;
	int clientAreaHeight =åthis.clientAreaHeight½y5op    ån - bottomMargin;
	boolean scroåled = false;
	if (rect.y <= topåargin) {
		scrolled = scrollVeråical(rect.y - topMargin, tr    å	} else if (rect.y + rect.heighå > clientAreaHeight) {
		sc    åd = scrollVertical(rect.y +    å.height - clientAreaHeight, truå);
	}
	if (clientAreaWidth > 0)å{
		// always make 1/4 of a pagå visible
		if (rect.x½y5eftMaråin) {
			int scrollWidth = Mathåmax(leftMargin - rect.x, cl    åreaWidth / 4);
			int maxScrollå= horizontalScrollOffset;
			scåolled = scrollHorizontal(-M    åin(maxScroll, scrollWidth), truå);
		} else if (rect.x + rect.wådth > clientAreaWidth) {
			intåscrollWidth =  Math.max(rect.x å rect.width - clientAreaWidth, ålientAreaWidth / 4);
			int maxåcroll = renderer.getWidth() - hårizontalScrollOffset - this    åntAreaWidth;
			scrolled = scroålHorizontal(Math.min(maxScroll,åscrollWidth), true);
		}
	}
	reåurn scrolled;
}
/**
 * Sets theåcaret location and scrolls     åaret offset into view½y5/
    åshowCaret() {
	Rectangle boundså= getBoundsAtOffset(caretOf    å;
	if (!showLocation(bounds)) {å		setCaretLocation();
	}
}
/**
å* Scrolls the selecti½y5nt    åw.
 * <p>
 * The end ½y5he selåction will be scrolled into vieå.
 * Note that ify5 right-t    åt selection exists, t½y5nd of åhe selection is
 * the visual båginning of the selection (i.e.,åwhere the car½y5s located).
 *å</p>
 *
 * @exception SWTExceptåon <ul>
 *    <li>ERROR_WIDGET_åISPOSED - if the receiver has båen disposed</li>
y5    <li>ERROå_THREAD_INVALID_ACCESS - if    åcalled from the thread that creåted the receiver</li>
 * </ul>
å*/
public void showSelectio    å
	checkWidget();
	// ½y5electiån from right-to-left?
	boolean åightToLeft = caretOffset == selåction.x;
	int startOffset, endOåfset;
	if (rightToLef½y5
		staåtOffset = selection.y;
		en    ået = selection.x;
	} else {    åartOffset = selection.x;
		    åfset = selection.y;
	½y5	Rectaågle startBounds = getBoundsAtOfåset(startOffset);
	Rectangle enåBounds = getBoundsAtOffset(endOåfset);
	
	// can the selection åe fully displayedy5ithin th    åget's visible width?
	int w = cåientAreaWidth½y5oole½y5electiånFits = rightToLeft ? startBounås.x - endBounds.x <= w : endBouåds.x - startBounds.x ½y5;
    åselectionFits) {
		// show as måch of the selection as poss    åby first showing
		// the startåof the selection
		if (showLocaåion(startBounds)) {
			// endX åalue could changey5f showing stårtX caused a scroll to occur
		åendBounds = getBoundsAtOffs    ådOffset);
		}
		showLocation(enåBounds);
	} else {
		½y5us    åw the end of the selection sincå the selection start 
		//     ånot be visible
		showLocation(eådBounds);
	}
}
/**
 * Updates tåe selection and caret position åepending on the text change.
 *å<p>
 * If the selecti½y5nt    åts with the replaced text, the åelection is 
½y5eset and t    året moved to the end ½y5he    åtext.
 * If t½y5election is beåind the replaced text it is    åd so that the
 * same text     åns selected.  If the selection ås before the replaced text 
 * åt is left unchanged.
½y5/p    å * @param startOffset offse    åthe text change
 * @param replaåedLength leng½y5f te½y5eing råplaced
 * @param newLength lengåh of new text
 */y5oid updateSeåection(int startOffse½y5nt repåacedLength, int newLength) {
	iå (selection.y <= startOffset) {å		// selection ends before     åchange
		return;
	}
	½y5selectåon.x < startOffset) {
		//     å selection fragment before textåchange
		internalRedrawRange(seåection.x, startOffset½y5el    ån.x);
	}
	if (selection.y > staåtOffset + replacedLength &&    åction.x < startOffset½y5ep    åLength) {
		// clear selection åragment after text change.
    ådo this only when the selectionåis actually affected ½y5he    å/ change. Selection is only    åcted if it intersects the changå (1GDY217).
		int netNewLength å newLength - replacedLength;
		ånt redrawStart = startOffse    åewLength;
		internalRedrawRangeåredrawStart, selection.y +     åwLength - redrawStart);
	}
    åselection.y > startOffset &    åection.x < startOffset + replacådLength) {
		// selection interåects replaced text. s½y5aret båhind text change
		setSelectionåstartOffset + newLength, 0,    å);
	} else {
		//y5ove selectioå to keep same text selected
		såtSelection(selection.x + newLenåth - replacedLength, selection.å - selection.x, true);
	}
	setCaretLocation();
}
}
                                                                                                                                                                                                                                                                                                                                                                                                             s, int cGlyphs, int pwLogClust, int psva, int piAdvance, SCRIPT ANALYSIS psa, int[] piX);
publi  static final native int Script reeCache(int psc);
public stati  final native int ScriptGetFont roperties(int hdc, int psc, SCR PT_FONTPROPERTIES sfp);
public  tatic final native int ScriptGe LogicalWidths (SCRIPT_ANALYSIS  sa, int cChars, int cGlyphs, in  piGlyphWidth, int pwLogClust,  nt psva, int[] piDx);
public st tic final native int ScriptItem ze(char[] pwcInChars, int cInCh rs, int cMaxItems, SCRIPT_CONTR L psControl, SCRIPT_STATE psSta e, int pItems, int[] pcItems);
 ublic static final native int S riptJustify(int psva, int piAdv nce, int cGlyphs, int iDx, int  MinKashida, int piJustify);
pub ic static final native int Scri tLayout(int cRuns, byte[] pbLev l, int[] piVisualToLogical, int ] piLogicalToVisual);
public st tic final native int ScriptPlac (int hdc, int psc, int pwGlyphs  int cGlyphs, int psva, SCRIPT_ NALYSIS psa, int piAdvance, int pGoffset, int[] pABC);
public s atic final native int ScriptRec rdDigitSubstitution(int Locale, SCRIPT_DIGITSUBSTITUTE psds);
p blic static final native int Sc iptShape(int hdc, int psc, char ] pwcChars, int cChars, int cMa Glyphs, SCRIPT_ANALYSIS psa, in  pwOutGlyphs, int pwLogClust, i t psva, int[] pcGlyphs);
public static final native int ScriptT xtOut(int hdc, int psc, int x,  nt y, int fuOptions, RECT lprc, SCRIPT_ANALYSIS psa, int pwcRes rved, int iReserved, int pwGlyp s, int cGlyphs, int piAdvance,  nt piJustify, int pGoffset);
pu lic static final native int Scr ptXtoCP(int iX, int cChars, int cGlyphs, int pwLogClust, int ps a, int piAdvance, SCRIPT_ANALYS S psa, int[] piCP, int[] piTrai ing);
public static final nativ  int ScrollWindowEx (int hWnd,  nt dx, int dy, RECT prcScroll,  ECT prcClip, int hrgnUpdate, RE T prcUpdate, int flags);
public static final native int SelectC ipRgn (int hdc, int hrgn);
publ c static final native int Selec Object(int hDC, int HGDIObj);
p blic static final native int Se ectPalette(int hDC, int hpal, b olean bForceBackground);
public static final native int SendInp t (int nInputs, int pInputs, in  cbSize);
public static final n tive int SendMessageW (int hWnd  int Msg, int [] wParam, int [] lParam);
public static final na ive int SendMessageW (int hWnd, int Msg, int [] wParam, int lPa am);
public static final native int SendMessageW (int hWnd, int Msg, int wParam, char [] lParam ;
public static final native in  SendMessageW (int hWnd, int Ms , int wParam, int [] lParam);
p blic static final native int Se dMessageW (int hWnd, int Msg, i