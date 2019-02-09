/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames
package pChess
import pGrid._

case class Draughts(turn: Int, ltMen: List[Cood], ltKings: List[Cood], dkMen: List[Cood], dkKings: List[Cood])
{
  def ltPieces = ltMen ++ ltKings
  def dkPieces = dkMen ++ dkKings
  def pieces =  ltPieces ++ dkPieces
  private[this] def inner(ltMen: List[Cood], ltKings: List[Cood], dkMen: List[Cood], dkKings: List[Cood]) =
    Some(Draughts(turn + 1, ltMen: List[Cood], ltKings, dkMen, dkKings))
  
  def wtTurn(inp: List[Cood]): Option[Draughts] =
  {    
    inp match
    {
      case _ if inp.length < 2 => None
      case List(s, e) if ltMen.contains(s) & !pieces.contains(e) & e.y == s.y + 1  => inner(ltMen.replace(s, e), ltKings, dkMen, dkKings)
      case _ => None
    }
  }
}

object Draughts
{
  def start: Draughts =
  {
    val xs = (0 to 3).toList.map(_ * 2)
    def f(dx: Int, y: Int): List[Cood] = xs.map(x => Cood(x + dx, y))
    val ltMen = f(1, 1) ++ f(2, 2) ++ f(1, 3)
    val dkMen = f(2, 6) ++ f(1, 7) ++ f(2, 8)
    Draughts(0, ltMen, Nil, dkMen, Nil)
  }
}
