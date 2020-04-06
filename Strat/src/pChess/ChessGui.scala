package ostrat
package pChess
import geom._, pCanv._, Colour._

case class ChessGui(canv: CanvasPlatform) extends CanvasNoPanels("Chess")
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
  val s1 = Refs(Queen, Rook)
  val p = Rook.scale(200)
  
  val stuff = Refs(p.fill(DarkRed))//grid.squares(tileWidth)// ::: grid.

  repaint(stuff)

  mouseUp =
    { case (LeftButton, cl, v) =>
      {
        deb(cl.toString)
        //selected = clickList.fHead(Nil, (h , _) => List(h))
        //statusText = selected.headOption.fold("Nothing Clicked")(_.toString)
        //eTop()
      }
      case _ => deb("Mouse other")
    }
}
