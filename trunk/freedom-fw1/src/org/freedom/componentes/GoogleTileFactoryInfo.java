package org.freedom.componentes;

import org.jdesktop.swingx.mapviewer.TileFactoryInfo;

public class GoogleTileFactoryInfo extends TileFactoryInfo {
  boolean sateliteView;
  public GoogleTileFactoryInfo (int minimumZoomLevel, int maximumZoomLevel, int totalMapZoom,
            int tileSize, boolean xr2l, boolean yt2b,
            boolean sateliteView) {
    super (minimumZoomLevel, maximumZoomLevel, totalMapZoom, tileSize, xr2l, yt2b, null, null, null, null);
    this.sateliteView = sateliteView;
  }
 
  @Override
  public String getTileUrl(int x, int y, int zoom) {
    if ( !sateliteView ) {
      return getMapUrl(x, y, zoom);
    } else {
      return getSatURL(x, y, zoom);
    }
  }
 
  protected String getMapUrl (int x, int y, int zoom)  {
	  
//  String url = "http://mt.google.com/mt?w=2.43" + "&x=" + x + "&y=" + y + "&zoom=" + zoom; // URL inativa...              
	String url = "http://mt.google.com/mt?v=w2.95" + "&x=" + x + "&y=" + y + "&zoom=" + zoom;
	
    return url;
  }

  /**
   * Foto de satelite. Não funcional.
   * @param x
   * @param y
   * @param z
   * @return
   */
  public String getSatURL(int x, int y, int zoom) {
    int ya = 1 << (17 - zoom);
    if ((y < 0) || (ya - 1 < y)) {
      return "http://www.google.com/mapfiles/transparent.gif";
    }
    if ((x < 0) || (ya - 1 < x)) {
      x = x % ya;
      if (x < 0) {
        x += ya;
      }
    }

    StringBuffer str = new StringBuffer();
    str.append('t');
    for (int i = 16; i >= zoom; i--) {
      ya = ya / 2;
      if (y < ya) {
        if (x < ya) {
          str.append('q');
        } else {
          str.append('r');
          x -= ya;
        }
      } else {
        if (x < ya) {
          str.append('t');
          y -= ya;
        } else {
          str.append('s');
          x -= ya;
          y -= ya;
        }
      }
    }
    return "http://kh.google.com/kh?v=1&t=" + str;
  }
} 