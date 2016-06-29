package org.sf.feeling.swt.win32.extension.widgets;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.Path;
import org.eclipse.swt.graphics.Pattern;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Region;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.sf.feeling.swt.win32.extension.graphics.GraphicsUtil;
import org.sf.feeling.swt.win32.extension.widgets.theme.GeneralThemeRender;
import org.sf.feeling.swt.win32.extension.widgets.theme.GlossyThemeRender;
import org.sf.feeling.swt.win32.extension.widgets.theme.OfficeThemeRender;
import org.sf.feeling.swt.win32.extension.widgets.theme.ThemeRender;
import org.sf.feeling.swt.win32.internal.extension.graphics.GCExtension;

public class CButton {

	private static final int SUBMENUWIDTH = 11;

	private static final int HORIZON_MARGIN = 5, VERTICAL_MARGIN = 2;

	private static final int DRAW_FLAGS = SWT.DRAW_TAB | SWT.DRAW_TRANSPARENT
			| SWT.DRAW_DELIMITER;

	private boolean menuTracked;

	private boolean selected = false;

	private boolean selection = false;

	private ThemeRender theme;

	private int style = SWT.NONE;

	public void setTheme(ThemeRender theme) {
		if (button == null || button.isDisposed())
			return;
		if (!GraphicsUtil.checkGdip() && theme instanceof GlossyThemeRender)
			return;
		this.theme = theme;
	}

	public CButton(Composite parent, int style, ThemeRender theme) {
		button = new Canvas(parent, SWT.NONE) {
			public Point computeSize(int wHint, int hHint, boolean changed) {
				checkWidget();
				Point size = recalculate();
				int width = size.x;
				int height = size.y;
				if (wHint != SWT.DEFAULT)
					width = wHint;
				if (hHint != SWT.DEFAULT)
					height = hHint;
				int border = getBorderWidth();
				width += border * 2;
				height += border * 2;
				return new Point(width, height);
			}
		};
		this.style = style;
		setTheme(theme);
		button.addPaintListener(new PaintListener() {

			public void paintControl(PaintEvent e) {
				onPaint(e);
			}

		});
		button.addMouseMoveListener(new MouseMoveListener() {
			public void mouseMove(MouseEvent e) {
				handleMouseMoveEvent(new Point(e.x, e.y));
			}
		});
		button.addMouseTrackListener(new MouseTrackAdapter() {
			public void mouseExit(MouseEvent e) {
				menuTracked = false;
				selected = false;
				refresh();
			}
		});

		button.addMouseListener(new MouseAdapter() {

			public void mouseDown(MouseEvent e) {
				if (!selected) {
					selected = true;
					refresh();
				}
			}

			public void mouseUp(MouseEvent e) {
				if (selected) {
					if (isEnabled()) {
						Event event = new Event();
						event.widget = button;
						event.data = this;
						event.type = SWT.SELECTED;
						fireSelectionEvent(event);
						Display.getDefault().asyncExec(new Runnable() {
							public void run() {
								refresh();
							}
						});
					}
					selected = false;
					refresh();
				}
			}

		});
	}

	private void onPaint(PaintEvent e) {
		drawSingleCommand(e.gc);
	}

	public Control getControl() {
		return button;
	}

	private void drawSingleCommand(GC gc) {
		Rectangle drawRect = new Rectangle(1, 1, button.getSize().x - 2, button
				.getSize().y - 2);

		{
			if ((getStyle() & SWT.TOGGLE) != 0 && getSelection()) {
				if (theme instanceof GeneralThemeRender) {
					GeneralThemeRender themeRender = (GeneralThemeRender) theme;
					gc.setForeground(themeRender
							.getTool_item_check_selection_bg_normal1());
					gc.setBackground(themeRender
							.getTool_item_check_selection_bg_normal2());
					gc.fillGradientRectangle(drawRect.x, drawRect.y,
							drawRect.width, drawRect.height, true);
					gc.setForeground(themeRender
							.getTool_item_check_selection_fg_normal());
					gc.drawRectangle(drawRect);
				} else if (theme instanceof OfficeThemeRender) {
					OfficeThemeRender themeRender = (OfficeThemeRender) theme;
					Color[] colors = new Color[] {
							themeRender.getTool_item_outer_top_checked1(),
							themeRender.getTool_item_outer_top_checked2(),
							themeRender.getTool_item_outer_bottom_checked1(),
							themeRender.getTool_item_outer_bottom_checked2(),
							themeRender.getTool_item_inner_top_checked1(),
							themeRender.getTool_item_inner_top_checked2(),
							themeRender.getTool_item_inner_bottom_checked1(),
							themeRender.getTool_item_inner_bottom_checked2(),
							themeRender.getTool_item_border_checked(), };
					drawGradientBack(gc, drawRect, colors);
					drawGradientBorder(gc, drawRect, colors);
				} else if (theme instanceof GlossyThemeRender) {
					drawGlossyItem(gc, drawRect);
				}
			}
			if (theme instanceof GeneralThemeRender) {
				GeneralThemeRender themeRender = (GeneralThemeRender) theme;
				if (selected && isEnabled()) {

					if (menuTracked || (getMenu() != null && !canSelected())) {
						gc.setForeground(themeRender
								.getTool_item_showmenu_bg1());
						gc.setBackground(themeRender
								.getTool_item_showmenu_bg2());
						gc.fillGradientRectangle(drawRect.x, drawRect.y,
								drawRect.width, drawRect.height, true);
						gc
								.setForeground(themeRender
										.getTool_item_showmenu_fg());
						gc.drawRectangle(drawRect);
					} else {
						gc.setForeground(themeRender
								.getTool_item_bg_selected1());
						gc.setBackground(themeRender
								.getTool_item_bg_selected2());
						if (getMenu() != null) {
							Rectangle noneMenuRect = getNonMenuRect();
							gc.fillGradientRectangle(noneMenuRect.x,
									noneMenuRect.y, noneMenuRect.width,
									noneMenuRect.height, true);
							gc.setForeground(themeRender
									.getTool_item_bg_track1());
							gc.setBackground(themeRender
									.getTool_item_bg_track2());
							Rectangle menuRect = getMenuRect();
							gc.fillGradientRectangle(menuRect.x, menuRect.y,
									menuRect.width, menuRect.height, true);
						} else {
							gc.fillGradientRectangle(drawRect.x, drawRect.y,
									drawRect.width, drawRect.height, true);
						}
						gc
								.setForeground(themeRender
										.getTool_item_fg_selected());
						gc.drawRectangle(drawRect);

					}
				} else if ((getStyle() & SWT.TOGGLE) != 0 && getSelection()
						&& isEnabled()) {
					gc.setForeground(themeRender
							.getTool_item_check_selection_bg_track1());
					gc.setBackground(themeRender
							.getTool_item_check_selection_bg_track2());
					gc.fillGradientRectangle(drawRect.x, drawRect.y,
							drawRect.width, drawRect.height, true);
					gc.setForeground(themeRender
							.getTool_item_check_selection_fg_track());
					gc.drawRectangle(drawRect);
				} else {
					gc.setForeground(themeRender.getTool_item_bg_track1());
					gc.setBackground(themeRender.getTool_item_bg_track2());
					gc.fillGradientRectangle(drawRect.x, drawRect.y,
							drawRect.width, drawRect.height, true);
					gc.setForeground(themeRender.getTool_item_fg_track());
					gc.drawRectangle(drawRect);
				}
			} else if (theme instanceof OfficeThemeRender) {
				OfficeThemeRender themeRender = (OfficeThemeRender) theme;
				Color[] colors = null;
				if (selected && isEnabled()) {

					if (menuTracked || (getMenu() != null && !canSelected())) {
						colors = new Color[] {
								themeRender
										.getTool_item_outer_top_showmenu_color1(),
								themeRender
										.getTool_item_outer_top_showmenu_color2(),
								themeRender
										.getTool_item_outer_bottom_showmenu_color1(),
								themeRender
										.getTool_item_outer_bottom_showmenu_color2(),
								themeRender
										.getTool_item_inner_top_showmenu_color1(),
								themeRender
										.getTool_item_inner_top_showmenu_color2(),
								themeRender
										.getTool_item_inner_bottom_showmenu_color1(),
								themeRender
										.getTool_item_inner_bottom_showmenu_color2(),
								themeRender
										.getTool_item_border_showmenu_color(), };
						drawGradientBack(gc, drawRect, colors);
						drawGradientBorder(gc, drawRect, colors);
					} else {
						colors = new Color[] {
								themeRender.getTool_item_outer_top_selected1(),
								themeRender.getTool_item_outer_top_selected2(),
								themeRender
										.getTool_item_outer_bottom_selected1(),
								themeRender
										.getTool_item_outer_bottom_selected2(),
								themeRender.getTool_item_inner_top_selected1(),
								themeRender.getTool_item_inner_top_selected2(),
								themeRender
										.getTool_item_inner_bottom_selected1(),
								themeRender
										.getTool_item_inner_bottom_selected2(),
								themeRender.getTool_item_border_selected(), };
						if (getMenu() != null) {
							drawGradientBack(gc, getNonMenuRect(), colors);
							colors = new Color[] {
									themeRender.getTool_item_outer_top_track1(),
									themeRender.getTool_item_outer_top_track2(),
									themeRender
											.getTool_item_outer_bottom_track1(),
									themeRender
											.getTool_item_outer_bottom_track2(),
									themeRender.getTool_item_inner_top_track1(),
									themeRender.getTool_item_inner_top_track2(),
									themeRender
											.getTool_item_inner_bottom_track1(),
									themeRender
											.getTool_item_inner_bottom_track2(),
									themeRender.getTool_item_border_track(), };
							drawGradientBack(gc, getMenuRect(), colors);
						} else
							drawGradientBack(gc, drawRect, colors);
						drawGradientBorder(gc, drawRect, colors);
					}

				} else if ((getStyle() & SWT.TOGGLE) != 0 && getSelection()
						&& isEnabled()) {
					colors = new Color[] {
							themeRender.getTool_item_outer_top_checked_track1(),
							themeRender.getTool_item_outer_top_checked_track2(),
							themeRender
									.getTool_item_outer_bottom_checked_track1(),
							themeRender
									.getTool_item_outer_bottom_checked_track2(),
							themeRender.getTool_item_inner_top_checked_track1(),
							themeRender.getTool_item_inner_top_checked_track2(),
							themeRender
									.getTool_item_inner_bottom_checked_track1(),
							themeRender
									.getTool_item_inner_bottom_checked_track2(),
							themeRender.getTool_item_border_checked_track(), };
					drawGradientBack(gc, drawRect, colors);
					drawGradientBorder(gc, drawRect, colors);
				} else {
					colors = new Color[] {
							themeRender.getTool_item_outer_top_track1(),
							themeRender.getTool_item_outer_top_track2(),
							themeRender.getTool_item_outer_bottom_track1(),
							themeRender.getTool_item_outer_bottom_track2(),
							themeRender.getTool_item_inner_top_track1(),
							themeRender.getTool_item_inner_top_track2(),
							themeRender.getTool_item_inner_bottom_track1(),
							themeRender.getTool_item_inner_bottom_track2(),
							themeRender.getTool_item_border_track(), };
					drawGradientBack(gc, drawRect, colors);
					drawGradientBorder(gc, drawRect, colors);
				}
			} else if (theme instanceof GlossyThemeRender) {
				drawGlossyItem(gc, drawRect);
			}

		}
		if (isEnabled()) {
			gc.setForeground(theme.getTool_item_fg());
		} else
			gc.setForeground(theme.getTool_item_fg_disabled());
		drawImageAndText(gc);
	}

	private void drawGradientBack(GC g, Rectangle rect, Color[] colors) {
		g.setAdvanced(true);
		Rectangle backRect = new Rectangle(rect.x, rect.y, rect.width + 1,
				rect.height + 1);
		Rectangle backRect1 = GraphicsUtil.inflate(backRect, -1, -1);
		int height = backRect1.height / 2;
		Rectangle rect1 = new Rectangle(backRect1.x, backRect1.y,
				backRect1.width, height);
		Rectangle rect2 = new Rectangle(backRect1.x, backRect1.y + height,
				backRect1.width, backRect1.height - height);

		g.setForeground(colors[0]);
		g.setBackground(colors[1]);
		g.fillGradientRectangle(rect1.x, rect1.y, rect1.width, rect1.height,
				true);

		g.setForeground(colors[2]);
		g.setBackground(colors[3]);
		g.fillGradientRectangle(rect2.x, rect2.y, rect2.width, rect2.height,
				true);

		Rectangle backRect2 = GraphicsUtil.inflate(backRect1, -1, -1);
		height = backRect2.height / 2;
		rect1 = new Rectangle(backRect2.x, backRect2.y, backRect2.width, height);
		rect2 = new Rectangle(backRect2.x, backRect2.y + height,
				backRect2.width, backRect2.height - height);

		g.setForeground(colors[4]);
		g.setBackground(colors[5]);
		g.fillGradientRectangle(rect1.x, rect1.y, rect1.width, rect1.height,
				true);

		g.setForeground(colors[6]);
		g.setBackground(colors[7]);
		g.fillGradientRectangle(rect2.x, rect2.y, rect2.width, rect2.height,
				true);

		g.setAdvanced(false);
	}

	private void drawGradientBorder(GC g, Rectangle rect, Color[] colors) {
		if (GraphicsUtil.checkGdip()) {
			g.setAdvanced(true);
			Rectangle backRect = new Rectangle(rect.x, rect.y, rect.width,
					rect.height);
			Path path = GraphicsUtil.createRoundPath(backRect, 1.2f);
			g.setForeground(colors[8]);
			g.drawPath(path);
			path.dispose();
			g.setAdvanced(false);
		} else {
			g.setForeground(colors[8]);
			g.drawRectangle(rect);
		}
	}

	private void drawGlossyItem(GC gc, Rectangle drawRect) {
		GlossyThemeRender themeRender = (GlossyThemeRender) theme;

		Rectangle outerBorder = new Rectangle(drawRect.x, drawRect.y,
				drawRect.width, drawRect.height);
		Rectangle innerBorder = GraphicsUtil.inflate(outerBorder, -1, -1);
		Rectangle glossy = new Rectangle(innerBorder.x, innerBorder.y,
				innerBorder.width, innerBorder.height / 2);
		Rectangle glow = GraphicsUtil.createRectangleFromLTRB(outerBorder.x,
				outerBorder.y + Math.round(outerBorder.height * .5f),
				outerBorder.x + outerBorder.width, outerBorder.y
						+ outerBorder.height);

		gc.setBackground(themeRender.getMenu_control_item_bg_track());
		gc.fillRectangle(innerBorder);

		gc.setAntialias(SWT.ON);

		Pattern pattern = new Pattern(null, 0, glossy.y, 0, glossy.y
				+ glossy.height, themeRender.getTool_item_bg_color1(),
				themeRender.getTool_item_bg_color1_alpha(), themeRender
						.getTool_item_bg_color2(), themeRender
						.getTool_item_bg_color2_alpha());
		Path path = new Path(null);
		path.addRectangle(glossy.x, glossy.y, glossy.width, glossy.height);
		gc.setBackgroundPattern(pattern);
		gc.fillPath(path);
		path.dispose();

		// draw border
		path = GraphicsUtil.createRoundRectangle(outerBorder, 2);
		gc.setForeground(themeRender.getTool_item_outer_border());
		gc.drawPath(path);
		path.dispose();

		if (selected && (menuTracked || (getMenu() != null && !canSelected()))) {
			path = GraphicsUtil.createTopRoundRectangle(innerBorder, 2);
			gc.setBackground(themeRender.getTool_item_bg_glossy_showMenu());
			gc.fillPath(path);
			path.dispose();

			path = GraphicsUtil.createTopRoundRectangle(innerBorder, 2);
			gc.setForeground(themeRender.getTool_item_bg_glossy_showMenu());
			gc.drawPath(path);
			path.dispose();
			gc.setAdvanced(false);
			return;
		} else if (selected && getMenu() != null) {
			pattern = new Pattern(null, 0, glossy.y, 0, glossy.y
					+ glossy.height, themeRender
					.getTool_item_bg_glossy_selected1(), themeRender
					.getTool_item_bg_glossy_selected1_alpha(), themeRender
					.getTool_item_bg_glossy_selected2(), themeRender
					.getTool_item_bg_glossy_selected2_alpha());
			Rectangle nonMenuRect = getNonMenuRect();
			Rectangle nonMenuGlossy = new Rectangle(innerBorder.x,
					innerBorder.y, nonMenuRect.width - 1,
					innerBorder.height / 2);
			path = createTopLeftRoundRectangle(nonMenuGlossy, 2);
			gc.setBackgroundPattern(pattern);
			gc.fillPath(path);
			path.dispose();

			pattern = new Pattern(null, 0, glossy.y, 0, glossy.y
					+ glossy.height, themeRender
					.getTool_item_bg_glossy_track1(), themeRender
					.getTool_item_bg_glossy_track1_alpha(), themeRender
					.getTool_item_bg_glossy_track2(), themeRender
					.getTool_item_bg_glossy_track2_alpha());

			Rectangle menuRect = getMenuRect();
			Rectangle menuGlossy = new Rectangle(menuRect.x, innerBorder.y,
					menuRect.width - 1, innerBorder.height / 2);
			path = createTopRightRoundRectangle(menuGlossy, 2);
			gc.setBackgroundPattern(pattern);
			gc.fillPath(path);
			path.dispose();

			Color innerBorderColor = themeRender
					.getTool_item_inner_border_selected();

			path = createLeftRoundPath(new Rectangle(innerBorder.x,
					innerBorder.y, nonMenuRect.width - 1, innerBorder.height),
					2);
			gc.setForeground(innerBorderColor);
			gc.drawPath(path);
			path.dispose();

			innerBorderColor = themeRender.getTool_item_inner_border_track();

			path = createRightRoundPath(new Rectangle(menuRect.x,
					innerBorder.y, menuRect.width, innerBorder.height), 2);
			gc.setForeground(innerBorderColor);
			gc.drawPath(path);
			path.dispose();

			path = GraphicsUtil.createRoundRectangle(glow, 2);
			gc.setClipping(path);
			path.dispose();

			Color glowColor = themeRender.getTool_item_bg_glow_track();
			path = createBottomRadialPath(glow);
			float[] point = new float[2];
			float[] bounds = new float[4];
			path.getBounds(bounds);
			point[0] = (bounds[0] + bounds[0] + bounds[2]) / 2f;
			point[1] = (bounds[1] + bounds[1] + bounds[3]) / 2f;
			GCExtension extension = new GCExtension(gc);
			extension.fillGradientPath(path, point, glowColor, 255,
					new Color[] { glowColor }, new int[] { 0 });
			path.dispose();
			gc.setClipping((Region) null);

			gc.setAdvanced(false);
			return;
		}

		if (!(selected))
			pattern = new Pattern(null, 0, glossy.y, 0, glossy.y
					+ glossy.height, themeRender
					.getTool_item_bg_glossy_track1(), themeRender
					.getTool_item_bg_glossy_track1_alpha(), themeRender
					.getTool_item_bg_glossy_track2(), themeRender
					.getTool_item_bg_glossy_track2_alpha());
		else
			pattern = new Pattern(null, 0, glossy.y, 0, glossy.y
					+ glossy.height, themeRender
					.getTool_item_bg_glossy_selected1(), themeRender
					.getTool_item_bg_glossy_selected1_alpha(), themeRender
					.getTool_item_bg_glossy_selected2(), themeRender
					.getTool_item_bg_glossy_selected2_alpha());
		path = GraphicsUtil.createTopRoundRectangle(glossy, 2);
		gc.setBackgroundPattern(pattern);
		gc.fillPath(path);
		path.dispose();

		Color innerBorderColor = (selected)
				|| ((getStyle() & SWT.TOGGLE) != 0 && getSelection()) ? themeRender
				.getTool_item_inner_border_selected()
				: themeRender.getTool_item_inner_border_track();

		path = GraphicsUtil.createRoundRectangle(innerBorder, 2);
		gc.setForeground(innerBorderColor);
		gc.drawPath(path);
		path.dispose();

		if (!((menuTracked || (getMenu() != null && !canSelected())) && selected)) {
			path = GraphicsUtil.createRoundRectangle(glow, 2);
			gc.setClipping(path);
			path.dispose();

			Color glowColor = themeRender.getTool_item_bg_glow_track();

			if ((getStyle() & SWT.TOGGLE) != 0 && getSelection()) {
				glowColor = themeRender.getTool_item_bg_checked_glow_track();

			}

			path = createBottomRadialPath(glow);
			float[] point = new float[2];
			float[] bounds = new float[4];
			path.getBounds(bounds);
			point[0] = (bounds[0] + bounds[0] + bounds[2]) / 2f;
			point[1] = (bounds[1] + bounds[1] + bounds[3]) / 2f;
			GCExtension extension = new GCExtension(gc);
			extension.fillGradientPath(path, point, glowColor, 255,
					new Color[] { glowColor }, new int[] { 0 });
			path.dispose();
			gc.setClipping((Region) null);
		}
		gc.setAdvanced(false);
	}

	private Path createBottomRadialPath(Rectangle glow) {
		float[] bounds = new float[4];
		bounds[0] = glow.x;
		bounds[1] = glow.y;
		bounds[2] = glow.width;
		bounds[3] = glow.height;
		bounds[0] -= bounds[2] * .35f;
		bounds[1] -= bounds[3] * .15f;
		bounds[2] *= 1.7f;
		bounds[3] *= 2.3f;
		return GraphicsUtil.createEllipsePath(bounds);
	}

	/*
	 * Three status: normal, hover(VS2003), disable.
	 */
	private void drawImageAndText(GC gc) {
		gc.setAdvanced(false);
		Rectangle drawRect = getSelectRect();
		if (getMenu() != null)
			drawRect = new Rectangle(drawRect.x, drawRect.y, drawRect.width
					- SUBMENUWIDTH, drawRect.height);
		int left = drawRect.x;
		int top = drawRect.y;
		int right = drawRect.width + left;
		int bottom = drawRect.height + top;
		int length = gc.textExtent(getText(), DRAW_FLAGS).x;
		int height = gc.getFontMetrics().getHeight();
		String text = null;
		if (getText().length() > 0)
			text = getText();
		int imageHight = image == null ? 0 : image.getImageData().height;
		int imageWidth = image == null ? 0 : image.getImageData().width;

		if (text != null && image != null) {

			if ((style & SWT.TOP) != 0) {
				int x = left
						+ Math
								.round((drawRect.width - image.getImageData().width) / 2f);
				int y = top
						+ Math
								.round((imageHight - image.getImageData().height) / 2f);
				gc.drawImage(image, x, y);
				gc.drawText(text, left
						+ Math.round((drawRect.width - length) / 2f), top
						+ imageHight + VERTICAL_MARGIN, DRAW_FLAGS);
			} else if ((style & SWT.BOTTOM) != 0) {
				int x = left
						+ Math
								.round((drawRect.width - image.getImageData().width) / 2f);
				int y = bottom
						- imageHight
						+ Math
								.round((imageHight - image.getImageData().height) / 2f);
				drawImage(gc, x, y);
				gc.drawText(text, left
						+ Math.round((drawRect.width - length) / 2f), top
						+ drawRect.height - imageHight - VERTICAL_MARGIN
						- height, DRAW_FLAGS);
			} else if ((style & SWT.RIGHT) != 0) {
				gc.drawText(text, right - imageWidth - HORIZON_GAP - length,
						top + Math.round((drawRect.height - height) / 2f),
						DRAW_FLAGS);
				int x = (right - imageWidth)
						+ (imageWidth - image.getImageData().width) / 2;
				int y = top + (drawRect.height - image.getImageData().height)
						/ 2;
				drawImage(gc, x, y);
			} else {
				int x = left + (imageWidth - image.getImageData().width) / 2;
				int y = top + (drawRect.height - image.getImageData().height)
						/ 2;
				drawImage(gc, x, y);
				gc.drawText(text, left + HORIZON_GAP + imageWidth, top
						+ Math.round((drawRect.height - height) / 2f),
						DRAW_FLAGS);
			}
		} else {
			if (text != null) {
				gc.drawText(text, left
						+ Math.round((drawRect.width - length) / 2f), top
						+ Math.round((drawRect.height - height) / 2f),
						DRAW_FLAGS);
			} else if (image != null) {
				int x = left
						+ Math
								.round((drawRect.width - image.getImageData().width) / 2f);
				int y = top
						+ Math
								.round((drawRect.height - image.getImageData().height) / 2f);
				drawImage(gc, x, y);
			}
		}

		if (getMenu() != null) {
			Rectangle menuRect = getMenuRect();
			gc.setAdvanced(false);
			if (isEnabled()) {
				gc.setBackground(theme.getTool_item_arrow_bg_track());

			} else
				gc.setBackground(theme.getTool_item_arrow_bg_disabled());
			GraphicsUtil.drawArrow(gc, menuRect, SWT.DOWN);

			if (!(theme instanceof OfficeThemeRender)) {
				if (theme instanceof GeneralThemeRender)
					gc.setForeground(((GeneralThemeRender) theme)
							.getTool_item_check_separater_color());
				else if (theme instanceof GlossyThemeRender)
					gc.setForeground(((GlossyThemeRender) theme)
							.getTool_item_check_separater_color());
				gc.drawLine(menuRect.x, menuRect.y + 1, menuRect.x, menuRect.y
						+ menuRect.height - 1);
			} else {
				if (!selected) {
					gc.setForeground(((OfficeThemeRender) theme)
							.getTool_item_check_separater_drakcolor());
					gc.drawLine(menuRect.x, menuRect.y + 1, menuRect.x,
							menuRect.y + menuRect.height - 1);
					gc.setForeground(((OfficeThemeRender) theme)
							.getTool_item_check_separater_lightcolor());
					gc.drawLine(menuRect.x + 1, menuRect.y + 1, menuRect.x + 1,
							menuRect.y + menuRect.height - 1);
				}
			}
		}
	}

	private static final int VERTICAL_GAP = 3;

	private static final int HORIZON_GAP = 2;

	private Rectangle getSelectRect() {
		Rectangle bound = new Rectangle(1, 1, button.getSize().x - 1, button
				.getSize().y - 1);
		Rectangle selectRect = new Rectangle(bound.x, bound.y, bound.width,
				bound.height);
		selectRect.height -= (2 * 2 + 1);
		selectRect.width -= (2 * 1 + 1);
		selectRect.x += HORIZON_GAP;
		selectRect.y += VERTICAL_GAP;
		return selectRect;
	}

	private Rectangle getMenuRect() {
		Rectangle bound = new Rectangle(1, 1, button.getSize().x - 2, button
				.getSize().y - 2);
		int menuLeft = getSelectRect().x + getSelectRect().width - SUBMENUWIDTH;
		int menuRight = bound.x + bound.width;
		Rectangle menuRect = new Rectangle(menuLeft, bound.y, menuRight
				- menuLeft, bound.height);
		return menuRect;
	}

	private Rectangle getNonMenuRect() {
		Rectangle bound = new Rectangle(1, 1, button.getSize().x - 2, button
				.getSize().y - 2);
		int left = bound.x;
		int right = getSelectRect().x + getSelectRect().width + 3
				- SUBMENUWIDTH;
		Rectangle menuRect = new Rectangle(left, bound.y, right - left,
				bound.height);
		return menuRect;
	}

	private void drawImage(GC gc, int x, int y) {
		if (image != null) {
			if (isEnabled()) {
				if (!selected
						&& (theme.isShowToolImageShadow() && GraphicsUtil
								.checkGdip())) {
					ImageData shadowImage = image.getImageData();
					PaletteData palette = new PaletteData(new RGB[] {
							new RGB(0, 0, 0), new RGB(154, 156, 146) });
					ImageData data = new ImageData(shadowImage.width,
							shadowImage.height, 1, palette);
					data.transparentPixel = shadowImage.transparentPixel;
					if (shadowImage.getTransparencyType() == SWT.TRANSPARENCY_PIXEL) {
						for (int pixelX = 0; pixelX < shadowImage.width; pixelX++) {
							for (int pixelY = 0; pixelY < shadowImage.width; pixelY++) {
								int dstPixel = shadowImage.getPixel(pixelX,
										pixelY);
								if (dstPixel != shadowImage.transparentPixel) {
									data.setPixel(pixelX, pixelY, 1);
								} else
									data.setPixel(pixelX, pixelY, 0);

							}
						}

						gc.setAdvanced(true);

						Image tempImage = new Image(gc.getDevice(), data);
						gc.drawImage(tempImage, x + 1, y + 1);
						tempImage.dispose();
						gc.setAdvanced(false);
						gc.drawImage(image, x - 1, y - 1);
					} else {
						gc.drawImage(image, x, y);
					}
				} else {
					if (GraphicsUtil.checkGdip()
							&& theme.isShowToolImageShadow()) {
						RGB[] rgbs = image.getImageData().palette.getRGBs();
						if (rgbs != null) {
							for (int i = 0; i < rgbs.length; i++) {
								RGB rgb = rgbs[i];
								rgb.red = (rgb.red + 76)
										- (((rgb.red + 32) / 64) * 19);
								rgb.green = (rgb.green + 76)
										- (((rgb.green + 32) / 64) * 19);
								rgb.blue = (rgb.blue + 76)
										- (((rgb.blue + 32) / 64) * 19);
							}
							ImageData shadowImage = image.getImageData();
							ImageData data = new ImageData(shadowImage.width,
									shadowImage.height, shadowImage.depth,
									new PaletteData(rgbs));
							data.transparentPixel = shadowImage.transparentPixel;
							for (int pixelX = 0; pixelX < shadowImage.width; pixelX++) {
								for (int pixelY = 0; pixelY < shadowImage.width; pixelY++) {
									int dstPixel = shadowImage.getPixel(pixelX,
											pixelY);
									if (dstPixel != shadowImage.transparentPixel) {
										data.setPixel(pixelX, pixelY, dstPixel);
									} else
										data.setPixel(pixelX, pixelY,
												shadowImage.transparentPixel);
								}
							}
							Image fadedImage = new Image(null, data);
							gc.drawImage(fadedImage, x, y);
							fadedImage.dispose();
						}
					} else
						gc.drawImage(image, x, y);
				}
			}
		} else {
			// Draw a image disabled
			Image disableImage = new Image(null, image, SWT.IMAGE_DISABLE);
			gc.drawImage(disableImage, x, y);
			disableImage.dispose();
		}

	}

	private Path createTopLeftRoundRectangle(Rectangle rectangle, int radius) {
		Path path = new Path(null);

		int l = rectangle.x;
		int t = rectangle.y;
		int w = rectangle.width;
		int h = rectangle.height;
		int d = radius << 1;

		path.addArc(l, t, d, d, -180, -90); // topleft
		path.lineTo(l + radius, t);
		path.lineTo(l + w, t);
		path.lineTo(l + w, t + h);
		path.lineTo(l, t + h);
		path.lineTo(l, t + radius);
		path.close();

		return path;
	}

	private Path createTopRightRoundRectangle(Rectangle rectangle, int radius) {
		Path path = new Path(null);

		int l = rectangle.x;
		int t = rectangle.y;
		int w = rectangle.width;
		int h = rectangle.height;
		int d = radius << 1;

		path.moveTo(l, t); // topleft
		path.lineTo(l + w - radius, t);
		path.addArc(l + w - d, t, d, d, -270, -90); // topright
		path.lineTo(l + w, t + radius);
		path.lineTo(l + w, t + h);
		path.lineTo(l, t + h);
		path.lineTo(l, t);
		path.close();

		return path;
	}

	public static Path createLeftRoundPath(Rectangle rectangle, int radius) {
		Path path = new Path(null);

		int l = rectangle.x;
		int t = rectangle.y;
		int w = rectangle.width;
		int h = rectangle.height;
		int d = radius << 1;

		path.moveTo(l + w, t);
		path.lineTo(l + radius, t);
		path.addArc(l, t, d, d, 90, 90);

		path.lineTo(l, t + radius);
		path.lineTo(l, t + h - radius);
		path.addArc(l, t + h - d, d, d, 180, 90);
		path.lineTo(l + radius, t + h);
		path.lineTo(l + w, t + h);

		return path;
	}

	public static Path createRightRoundPath(Rectangle rectangle, int radius) {
		Path path = new Path(null);

		int l = rectangle.x;
		int t = rectangle.y;
		int w = rectangle.width;
		int h = rectangle.height;
		int d = radius << 1;

		path.moveTo(l, t);
		path.lineTo(l + w - radius, t);
		path.addArc(l + w - d, t, d, d, -270, -90);
		path.lineTo(l + w, t + radius);
		path.lineTo(l + w, t + h - radius);
		path.addArc(l + w - d, t + h - d, d, d, 0, -90);
		path.lineTo(l + w - radius, t + h);
		path.lineTo(l, t + h); // top

		return path;
	}

	private String text = "";

	public String getText() {
		return text;
	}

	public void setText(String text) {
		if (text == null)
			this.text = "";
		else
			this.text = text;
	}

	private Image image;

	public boolean isVisible() {
		return visible;
	}

	private boolean visible = true;

	private boolean enabled = true;

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		refresh();
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	private CMenu menu;

	public void setMenu(CMenu menu) {
		this.menu = menu;
	}

	public CMenu getMenu() {
		return menu;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public int getStyle() {
		return style;
	}

	public boolean getSelection() {
		return selection;
	}

	public void setSelection(boolean selection) {
		this.selection = selection;
	}

	public boolean canSelected() {
		if (getMenu() == null)
			return true;
		return listeners != null;
	}

	private List listeners;

	private Canvas button;

	public void addSelectionListener(SelectionListener listener) {
		if (button.isDisposed())
			return;
		if (listener == null)
			return;
		if (listeners == null)
			listeners = new ArrayList();
		if (!listeners.contains(listener))
			listeners.add(listener);
	}

	public void removeSelectionListener(SelectionListener listener) {
		if (button.isDisposed())
			return;
		if (listener == null)
			return;
		else {
			listeners.remove(listener);
			if (listeners.size() == 0)
				listeners = null;
		}
	}

	void fireSelectionEvent(final Event event) {
		if (button.isDisposed())
			return;
		if (listeners != null) {
			final SelectionEvent selectEvent = new SelectionEvent(event);
			for (int i = 0; i < listeners.size(); i++) {
				final SelectionListener listener = (SelectionListener) listeners
						.get(i);
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						listener.widgetSelected(selectEvent);
					}
				});
			}
		}
	}

	public void disposed() {
		listeners.clear();
		listeners = null;
		button.dispose();
	}

	public boolean isDisposed() {
		return button.isDisposed();
	}

	private Point recalculate() {
		GC gc = new GC(button);
		int BREADTH_GAP = (gc.getFontMetrics().getHeight() / 3) + 1;
		int rowHeight = gc.getFontMetrics().getHeight() + BREADTH_GAP * 2;
		if ((style & SWT.TOP) != 0 || (style & SWT.BOTTOM) != 0) {
			if (image != null)
				rowHeight += image.getImageData().height;
			rowHeight += 2 * VERTICAL_MARGIN;
		} else {
			if (image != null)
				rowHeight = (image.getImageData().height > rowHeight ? image
						.getImageData().height : rowHeight);
		}
		int cellLength = 0;
		int cellMinLength = HORIZON_MARGIN * 2;
		if (getText() == null) {
			cellLength = cellMinLength;
			if (image != null)
				cellLength += image.getImageData().width;
		} else if (image == null) {
			cellLength = cellMinLength + gc.textExtent(getText(), DRAW_FLAGS).x;
		} else {
			if ((style & SWT.TOP) == 0 && (style & SWT.BOTTOM) == 0) {
				cellLength = cellMinLength
						+ gc.textExtent(getText(), DRAW_FLAGS).x
						+ HORIZON_MARGIN;
				if (image != null)
					cellLength += image.getImageData().width;
			} else {
				cellLength = gc.textExtent(getText(), DRAW_FLAGS).x;
				cellLength = cellLength < image.getImageData().width ? image
						.getImageData().width : cellLength;
				cellLength += (cellMinLength);
			}
		}
		if (getMenu() != null)
			cellLength += SUBMENUWIDTH;
		gc.dispose();
		return new Point(cellLength, rowHeight);
	}

	public void setLayoutData(Object layoutData) {
		button.setLayoutData(layoutData);
	}

	public Object getLayoutData() {
		return button.getLayoutData();
	}

	private MenuControl popupMenu;

	private boolean isShowMenu() {
		return popupMenu != null && popupMenu.getShell() != null
				&& !popupMenu.getShell().isDisposed();
	}

	public void refresh() {
		if (button != null && !button.isDisposed())
			button.redraw();
	}

	private void handleMouseMoveEvent(Point screenPos) {
		if (!new Rectangle(0, 0, getBounds().width, getBounds().height)
				.contains(screenPos)) {
			if (menuTracked || selected) {
				menuTracked = false;
				selected = false;
				refresh();
			}
		} else if (!isShowMenu() && getMenu() != null) {
			if (getMenuRect().contains(screenPos)) {
				if (!menuTracked) {
					menuTracked = true;
					refresh();
				}
			} else {
				if (menuTracked) {
					menuTracked = false;
					refresh();
				}
			}
		}
	}

	public void setLocation(Point location) {
		if (button != null && !button.isDisposed())
			button.setLocation(location);
	}

	public void setSize(Point size) {
		if (button != null && !button.isDisposed())
			button.setSize(size);
	}

	public void setBounds(Rectangle bound) {
		if (button != null && !button.isDisposed())
			button.setBounds(bound);
	}

	public Point getLocation() {
		if (button != null && !button.isDisposed())
			return button.getLocation();
		else
			return null;
	}

	public Point getSize() {
		if (button != null && !button.isDisposed())
			return button.getSize();
		else
			return null;
	}

	public Rectangle getBounds() {
		if (button != null && !button.isDisposed())
			return button.getBounds();
		else
			return null;
	}

	public Point computeSize(int wHint, int hHint) {
		if (button != null && !button.isDisposed())
			return button.computeSize(wHint, hHint);
		else
			return null;
	}
}
