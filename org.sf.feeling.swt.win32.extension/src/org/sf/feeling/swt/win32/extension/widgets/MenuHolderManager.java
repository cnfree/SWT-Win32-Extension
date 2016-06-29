package org.sf.feeling.swt.win32.extension.widgets;

import java.util.Hashtable;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class MenuHolderManager
{
	private static Hashtable manager = new Hashtable();

	private static Vector holders = new Vector();
	static
	{
		Display.getDefault().addFilter(SWT.KeyDown, new Listener()
		{

			public void handleEvent(Event e)
			{
				for (int i = 0; i < holders.size(); i++)
				{
					MenuHolder holder = (MenuHolder) holders.get(i);
					if (holder.getShell() != null && holder.getShell().isDisposed())
					{
						holders.remove(holder);
						i--;
						continue;
					}
					if (holder.isAcitve()) return;
				}
				Shortcut shortcut = new Shortcut(e.keyCode);
				shortcut.setControl((e.stateMask & SWT.CTRL) != 0);
				shortcut.setShift((e.stateMask & SWT.SHIFT) != 0);
				shortcut.setAlt((e.stateMask & SWT.ALT) != 0);
				Shell shell = Display.getDefault().getActiveShell();
				ShortcutMap map = (ShortcutMap) manager.get(shell);
				if (map != null && map.contains(shortcut))
				{
					e.data = map.getMenuItem(shortcut);
					if (map.getMenuItem(shortcut).isEnabled()) map.getMenuItem(shortcut)
							.fireSelectionEvent(e);
				}
			}
		});
	}

	public static MenuHolder getActiveHolder()
	{
		for (int i = 0; i < holders.size(); i++)
		{
			MenuHolder holder = (MenuHolder) holders.get(i);
			if (holder.getShell() != null && holder.getShell().isDisposed())
			{
				holders.remove(holder);
				i--;
				continue;
			}
			if (holder.isAcitve()) return holder;
		}
		return null;
	}

	public static void registryShortcut(MenuHolder holder)
	{
		registerHolder(holder);
		final Shell shell = holder.getShell();
		if (shell == null || shell.isDisposed() || holder.getMenu() == null) return;
		if (!manager.containsKey(shell))
		{
			manager.put(shell, new ShortcutMap());
			shell.addDisposeListener(new DisposeListener()
			{
				public void widgetDisposed(DisposeEvent e)
				{
					if (manager.containsKey(shell))
					{
						((ShortcutMap) manager.get(shell)).dispose();
						manager.remove(shell);
					}
				}
			});
		}
		ShortcutMap shortcutMap = (ShortcutMap) manager.get(shell);
		insertShortcut(shortcutMap, holder.getMenu());
	}

	public static void deRegistryShortcut(MenuHolder holder, Shortcut shortcut)
	{
		final Shell shell = holder.getShell();
		if (shell == null || shell.isDisposed() || holder.getMenu() == null) return;
		if (!manager.containsKey(shell))
		{
			return;
		}
		ShortcutMap shortcutMap = (ShortcutMap) manager.get(shell);
		shortcutMap.removeShortcut(shortcut);
	}

	public static void registerHolder(MenuHolder holder)
	{
		if (!holders.contains(holder)) holders.add(holder);
	}

	private static void insertShortcut(ShortcutMap map, CMenu menu)
	{
		for (int i = 0; i < menu.getItemCount(); i++)
		{
			CMenuItem item = menu.getItem(i);
			if (item.getMenu() != null)
			{
				insertShortcut(map, item.getMenu());
			}
			else if (item.getShortcut() != null) map.putShortcut(item.getShortcut(), item);
		}
	}

	public static void deRegistryShortcut(MenuHolder holder)
	{
		Shell shell = holder.getShell();
		if (shell == null || shell.isDisposed() || !manager.containsKey(shell)) return;
		ShortcutMap shortcutMap = (ShortcutMap) manager.get(shell);
		removeShortcut(shortcutMap, holder.getMenu());
	}

	private static void removeShortcut(ShortcutMap shortcutMap, CMenu menu)
	{
		for (int i = 0; i < menu.getItemCount(); i++)
		{
			CMenuItem item = menu.getItem(i);
			if (item.getMenu() != null)
			{
				removeShortcut(shortcutMap, item.getMenu());
			}
			else if (item.getShortcut() != null) shortcutMap
					.removeShortcut(item.getShortcut());
		}
	}
}
