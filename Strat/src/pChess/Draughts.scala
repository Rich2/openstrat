/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pChess
//import pGrid._


//  val tiles: Seq[CheckersSq] = for { y <- 1 to rowSize; x <- 1 to rowSize } yield Cood(x, y) match
//  { case c @ Cood(x, y) if c.evenSum & y <= 3 => DarkSq(x, y, Some(BlackPiece))
//    case c @ Cood(x, y) if c.evenSum & y >= 6 => DarkSq(x, y, Some(WhitePiece))
//    case c @ Cood(x, y) if c.evenSum          => DarkSq(x, y, None)
//    case c @ Cood(x, y)                           => LightSq(x, y)
//  }
object Draughts
{
  /*def start: Draughts =
  {
    val xs = (0 to 3).toList.map(_ * 2)
    def f(dx: Int, y: Int): List[Cood] = xs.map(x => Cood(x + dx, y))
    val ltMen = f(1, 1) ++ f(2, 2) ++ f(1, 3)
    val dkMen = f(2, 6) ++ f(1, 7) ++ f(2, 8)
    Draughts(0, ltMen, Nil, dkMen, Nil)
  }*/
}
