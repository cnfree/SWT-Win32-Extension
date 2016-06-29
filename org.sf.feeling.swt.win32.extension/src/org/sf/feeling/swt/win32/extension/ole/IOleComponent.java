
package org.sf.feeling.swt.win32.extension.ole;

public interface IOleComponent
{
	public void invokeOleFunction( String[] functionPath, Object[] args );

	public Object invokeOleFunctionWithResult( String[] functionPath,
			Object[] args );

	public boolean setOleProperty( String[] propertyPath, Object[] args );

	public Object getOleProperty( String[] propertyPath, Object[] args );

}
