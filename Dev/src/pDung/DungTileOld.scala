package ostrat
package pDung
import pGrid._

case class DTileOld(x: Int, y: Int, terr: DungTerr) extends ColouredTileOld//GridElem
{
  type FromT = DungTerr
  def fromT = terr
  var charac: OptRef[Character] = NoRef
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

