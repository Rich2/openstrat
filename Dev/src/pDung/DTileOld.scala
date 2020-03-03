/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pDung
import Colour._, pGrid._

sealed trait DTerr extends AnyRef with PersistSingleton
{ def colour: Colour
}

object DTerr { implicit val toDTile: (Int, Int, DTerr) => DTileOld = DTileOld.apply }

object Open extends DTerr
{ override def colour: Colour = Violet
  val str: String = "Open"
}

object Wall extends DTerr
{ override def colour: Colour = fromInts(80, 80, 80)
  val str: String = "Wall"
}

case class DTileOld(x: Int, y: Int, terr: DTerr) extends ColouredTileOld//GridElem
{
  type FromT = DTerr
  def fromT = terr
  var charac: EMon[Character] = NoGood
  def colour = terr.colour
}

object DTileOld
{
  implicit object DTerrIsType extends IsType[DTileOld]
  {
    override def isType(obj: AnyRef): Boolean = obj.isInstanceOf[DTileOld]
    override def asType(obj: AnyRef): DTileOld = obj.asInstanceOf[DTileOld]
  }
   
 // SqDBuilder extends PBuilderSingletons[DTerr]
//   {
//      override def persistType = "DTerr"
//      override def isType(obj: Any): Boolean = obj.isInstanceOf[DTerr]
//      def persistSingletons: Seq[DTerr] = Seq(Open, Rock)
//   }
}
