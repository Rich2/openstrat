/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames
package pZug
import Colour._
import pGrid._

trait ZugTerr
{
  def colour: Colour
  def cost: OptInt = SomeInt(1)
  def conceal: Boolean = false
}

object ZugTerr
{
  //implicit val zugMakerImplicit: (Int, Int, ZugTerr) => ZugTile = ZugTile.apply(_, _, _, Nil)
}

case class ZugTile(x: Int, y: Int, terr: ZugTerr, lunits: Arr[Squad] = Arr()) extends ColouredTile
{   
  def colour = terr.colour     
}

object ZugTile
{
  implicit val zugMakerImplicit: (Int, Int, ZugTerr) => ZugTile = apply(_, _, _)//???//ZugTile.apply(_, _, _, Nil)
  implicit val tileMove: (Int, Int, ZugTile) => ZugTile = (x, y, t) => t.copy(x, y)//apply(_, _, _.// = ZugTile( 
  implicit object ZugTileIsType extends IsType[ZugTile]
  {
    override def isType(obj: AnyRef): Boolean = obj.isInstanceOf[ZugTile]
    override def asType(obj: AnyRef): ZugTile = obj.asInstanceOf[ZugTile]   
  }   
}

case object Plain extends ZugTerr
{
   override def colour = LightGreen
}

case object WheatField extends ZugTerr
{
   override def colour = Wheat
}

case object Hill extends ZugTerr
{
   override def colour = Brown
}

trait Building extends ZugTerr { override def conceal = true }

case object StoneBuilding extends Building
{
   override def colour = Gray
   override def cost: OptInt = SomeInt(3)
}

object WoodBuilding extends Building
{
   override def colour = Brown
}

object Lake extends ZugTerr
{
   override def colour = Blue
   override def cost: OptInt = NoInt
}
