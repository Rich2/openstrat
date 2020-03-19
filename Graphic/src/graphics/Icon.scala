package ostrat
package geom

trait Icon extends UnScaled
{ type TranserT <: GraphicBounded
  def fTrans(f: Vec2 => Vec2): TranserT = apply()
}
/*
trait PolygonIcon
{

}

trait PolygonHeightIcon extends PolygonIcon
{
  def width: Double
}*/
