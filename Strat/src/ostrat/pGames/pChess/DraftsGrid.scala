/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames
package pChess
import Colour._, pGrid._, geom._, reflect.ClassTag

class DGrid extends SquareGrid[DTile, SideBare](1, 8, 1, 8, 0)// extends AnyVal
{  
  //def get(row: Int, col: Int): Option[Draught] = arr((row - 1) * 8 + col - 1)
  //def set(row: Int, col: Int, value: Option[Draught]): Unit = arr((row - 1) * 8 + col - 1) = value
  def setSome(row: Int, col: Int, value: Draught): Unit = arr((row - 1) * 8 + col - 1) = ???//value//Some(value)
//  def copy: DGrid = 
//  {
//    val newArr = new Array[Option[Draught]](64)
//    var count = 0
//    while( count < 64){ newArr(count) = arr(count); count += 1 }
//    new DGrid(newArr)
//  }
  def squares(tileWidth: Double): Arr[PolyFill] =
  iiToMap(1, 8){ (x, y) => Square.fill(tileWidth, x.ifSumOdd(Brown, Pink, y), (x - 4.5) * tileWidth, (y -4.5) * tileWidth) }

  def rowSize = 8
  def rowCen = (1.0 + rowSize) / 2.0
  def adj(i: Int) = i - rowCen
  
//  def pieces(tileWidth: Double): List[GraphicElem[_]] =
//  {
//    val res = for
//    {
//      x <- 1 to 8
//      y <- 1 to 8
//    } yield get(x, y) match
//    {
//      case Some(p)  => List(Circle.fillSubj(tileWidth / 1.6, p, p.colour, tileWidth * adj(x), tileWidth * adj(y))) 
//      case None => Nil        
//    }
//    res.toList.flatten 
//  }
}

object DGrid
{
  def start: DGrid =
  {
    val dg = new DGrid
    val ser = 0 to 6 by 2
    //ser.foreach(i => dg.setTile(2, i + 1, DTile(1, i + 1)))
    /*ser.foreach(i => dg.setSome(2, i + 2, BlackD))
    ser.foreach(i => dg.setSome(3, i + 2, BlackD))
    ser.foreach(i => dg.setSome(6, i + 2, WhiteD))
    ser.foreach(i => dg.setSome(7, i + 1, WhiteD))
    ser.foreach(i => dg.setSome(8, i + 2, WhiteD))*/
    dg
  }
}

//class ChessGrid[TileT <: Tile](length: Int, turnNum: Int)(implicit evTile: ClassTag[TileT]) extends SquareGrid[TileT, SideBare](1, length, 1, length,
 //   turnNum){}
