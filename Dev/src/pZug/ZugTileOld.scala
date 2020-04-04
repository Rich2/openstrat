/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pZug
import pGrid._

case class ZugTileOld(x: Int, y: Int, terr: ZugTerr, lunits: ArrOld[SquadOld] = ArrOld()) extends ColouredTileOld
{ type FromT = ZugTerr
  def fromT = terr
  def colour = terr.colour     
}

object ZugTileOld
{
  implicit val zugMakerImplicit: (Int, Int, ZugTerr) => ZugTileOld = apply(_, _, _)//???//ZugTile.apply(_, _, _, Nil)
  implicit val tileMove: (Int, Int, ZugTileOld) => ZugTileOld = (x, y, t) => t.copy(x, y)//apply(_, _, _.// = ZugTile(
  implicit object ZugTileIsType extends IsType[ZugTileOld]
  {
    override def isType(obj: AnyRef): Boolean = obj.isInstanceOf[ZugTileOld]
    override def asType(obj: AnyRef): ZugTileOld = obj.asInstanceOf[ZugTileOld]
  }   
}

case class ZugSideOld(x: Int, y: Int, wall: Boolean) extends TileSideOld
{

}

object ZugSideOld
{
  implicit val zugSideMakerImplicit: (Int, Int, Boolean) => ZugSideOld = ZugSideOld.apply

  implicit object ZugSideIsType extends IsType[ZugSideOld]
  {
    override def isType(obj: AnyRef): Boolean = obj.isInstanceOf[ZugSideOld]
    override def asType(obj: AnyRef): ZugSideOld = obj.asInstanceOf[ZugSideOld]
  }
}