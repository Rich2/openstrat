/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pChess
import geom._, pCanv._, Colour._

case class DraughtsGui(canv: CanvasPlatform) extends CanvasNoPanels("Draughts")
{
  var player = true

  val darkSquareColour = Brown
  val lightSquareColour = Pink  
  
//  val tiles: Seq[CheckersSq] = for { y <- 1 to rowSize; x <- 1 to rowSize } yield Cood(x, y) match
//  { case c @ Cood(x, y) if c.evenSum & y <= 3 => DarkSq(x, y, Some(BlackPiece))
//    case c @ Cood(x, y) if c.evenSum & y >= 6 => DarkSq(x, y, Some(WhitePiece))
//    case c @ Cood(x, y) if c.evenSum          => DarkSq(x, y, None)
//    case c @ Cood(x, y)                           => LightSq(x, y)          
//  }  
  
  val grid = DGridOld.start
 // deb(grid.getTile(1, 1).toString)
  val margin = 15
  val tileWidth: Double = ((height.min(width) - margin * 2).max(100) / grid.rowSize)
  val s1 = "Drafts".graphic(36, colour = Black)
  val stuff = grid.squares(tileWidth)// ::: grid.

  repaint(stuff)
  
  mouseUp = (v, but: MouseButton, clickList) => (v, but, clickList) match
  {
    case (v, LeftButton, cl) =>
    {
      deb(cl.toString)
      //selected = clickList.fHead(Nil, (h , _) => List(h))
      //statusText = selected.headOption.fold("Nothing Clicked")(_.toString)
      //eTop()
    }
    case _ => deb("Mouse other")
  }    
}
