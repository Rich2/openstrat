/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pDung
import Colour._, pGrid._

sealed trait DTerr extends AnyRef with PersistSingleton
{ def colour: Colour
}

object DTerr { implicit val toDTile: (Int, Int, DTerr) => DTile = DTile.apply }

object Open extends DTerr
{ override def colour: Colour = Violet
  val str: String = "Open"
}

object Wall extends DTerr
{ override def colour: Colour = fromInts(80, 80, 80)
  val str: String = "Wall"
}

case class DTile(x: Int, y: Int, terr: DTerr) extends ColouredTile//GridElem
{
  type FromT = DTerr
  def fromT = terr
  var charac: EMon[Character] = NoGood()
  def colour = terr.colour
}

object DTile
{
  implicit object DTerrIsType extends IsType[DTile]
  {
    override def isType(obj: AnyRef): Boolean = obj.isInstanceOf[DTile]
    override def asType(obj: AnyRef): DTile = obj.asInstanceOf[DTile]   
  }
   
 // SqDBuilder extends PBuilderSingletons[DTerr]
//   {
//      override def persistType = "DTerr"
//      override def isType(obj: Any): Boolean = obj.isInstanceOf[DTerr]
//      def persistSingletons: Seq[DTerr] = Seq(Open, Rock)
//   }
}
