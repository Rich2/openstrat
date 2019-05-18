package ostrat
package geom

trait Vec2Gen[A, VT]
{
  def xx(v: VT): Double
  def yy(v: VT): Double
  def newVec(x: Double, y: Double): VT
}

class Vec2GenExtension[A, VT](op: VT, ev: Vec2Gen[A, VT])
{
  implicit class Genimplict(value: VT)
  {
    def x: Double = ev.xx(value)
    def y: Double = ev.yy(value)
  }
  @inline def Vec(x: Double, y: Double): VT = ev.newVec(x, y)
  @inline def +(other: VT): VT = Vec(op.x + other.x, op.y + other.y)
}