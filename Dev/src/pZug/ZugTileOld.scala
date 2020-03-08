/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pZug
import Colour._, pGrid._

trait ZugTerr
{ def colour: Colour
  def cost: EMonInt = GoodInt(1)
  def conceal: Boolean = false
}

object ZugTerr
{
  //implicit val zugMakerImplicit: (Int, Int, ZugTerr) => ZugTile = ZugTile.apply(_, _, _, Nil)
}

case class ZugTileOld(x: Int, y: Int, terr: ZugTerr, lunits: ArrOld[Squad] = ArrOld()) extends ColouredTileOld
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

case object Plain extends ZugTerr
{ override def colour = LightGreen
}

case object WheatField extends ZugTerr
{ override def colour = Wheat
}

case object Hill extends ZugTerr
{ override def colour = Brown
}

trait Building extends ZugTerr { override def conceal = true }

case object StoneBuilding extends Building
{ override def colour = Gray
  override def cost: EMonInt = GoodInt(3)
}

object WoodBuilding extends Building
{ override def colour = Brown
}

object Lake extends ZugTerr
{ override def colour = Blue
  override def cost: EMonInt = BadIntNo
}