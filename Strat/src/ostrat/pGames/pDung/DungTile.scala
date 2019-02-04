/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames
package pDung
import Colour._
import pGrid._

sealed trait DTerr extends AnyRef with SingletonLeaf
{ def typeSym = 'DTerr
  def colour: Colour
}

object DTerr { implicit val toDTile: (Int, Int, DTerr) => DungTile = DungTile.apply }

object Open extends DTerr// with PersisterSingleton
{ override def colour: Colour = Violet
  val objSym = 'Open
}

object Wall extends DTerr
{ override def colour: Colour = fromInts(80, 80, 80)
  val objSym = 'Wall
}

case class DungTile(x: Int, y: Int, terr: DTerr) extends ColouredTile//GridElem
{ var charac: Opt[Character] = nullRef
  def colour = terr.colour
}

object DungTile
{
   implicit object DTerrIsType extends IsType[DungTile]
   {
      override def isType(obj: AnyRef): Boolean = obj.isInstanceOf[DungTile]
      override def asType(obj: AnyRef): DungTile = obj.asInstanceOf[DungTile]   
   }
   
 // SqDBuilder extends PBuilderSingletons[DTerr]
//   {
//      override def persistType = "DTerr"
//      override def isType(obj: Any): Boolean = obj.isInstanceOf[DTerr]
//      def persistSingletons: Seq[DTerr] = Seq(Open, Rock)
//   }
}
