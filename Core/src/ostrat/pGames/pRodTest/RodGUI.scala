/* Copyright 2018 w0d. Licensed under Apache Licence version 2.0 */

package ostrat
package pGames.pRodTest

import geom._, pCanv._, Colour._

case class RodGUI (canv: CanvasPlatform) extends CanvasSimple {
  val title = "still nothing to say.."
  val rndY = new scala.util.Random().nextInt(300)  
  val rndX = new scala.util.Random().nextInt(300)  
  var ptStart: Vec2 = 200 vv rndY
  canv.textGraphic(ptStart, title, 18, LightBlue)
  ptStart = rndX vv rndY  
 canv.textGraphic(ptStart, title, 18, LightBlue)
  
} //hi