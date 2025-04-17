/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A Dodecahedron aligned with the X and Y Axis so v0 is vertically up from the centre and v6 vertically down. */
class DoDeclign(val arrayUnsafe: Array[Double]) extends AnyVal, Polygon6Plus
{ override type ThisT = DoDeclign
  override def typeStr: String = "DoDeclign"
  override def fromArray(array: Array[Double]): DoDeclign = new DoDeclign(array)
  override def v0x: Double = arrayUnsafe(0)
  override def v0y: Double = arrayUnsafe(1)
  override def v0: Pt2 = Pt2(arrayUnsafe(0), arrayUnsafe(1))
  override def vLastX: Double = arrayUnsafe(numVerts - 2)
  override def vLastY: Double = arrayUnsafe(numVerts - 1)
  override def vLast: Pt2 = Pt2(vLastX, vLastY)
  override def side0: LineSeg = LineSeg(v0x, v0y, vertX(1), vertY(1))
  override def sd0CenX: Double = v0x \/ vertX(1)
  override def sd0CenY: Double = v0y \/ vertY(1)
  override def sd0Cen: Pt2 = Pt2(sd0CenX, sd0CenY)
  override def vertX(index: Int): Double = arrayUnsafe(index * 2)
  override def vertY(index: Int): Double = arrayUnsafe(index * 2 + 1)
  override def unsafeNegX: Array[Double] = arrayD1Map(d => -d)
  override def unsafeNegY: Array[Double] = arrayD2Map(d => -d)
  override def sides: LineSegArr = new LineSegArr(arrayForSides)
}

object DoDeclign
{
  def apply(radiusOut: Double, cen: Pt2): DoDeclign = DoDeclign(radiusOut, cen.x, cen.y)

  def apply(radiusOut: Double, cx: Double = 0, cy: Double = 0): DoDeclign =
  {
    val array = Array[Double](
      cx, cy + radiusOut,
      cx + Cos60 * radiusOut, cy + Sin60 * radiusOut,
      cx + Cos30 * radiusOut, cy + Sin30 * radiusOut,
      cx + radiusOut,         cy,
      cx + Cos30 * radiusOut, cy - Sin30 * radiusOut,
      cx + Cos60 * radiusOut, cy - Sin60 * radiusOut,
      cx,                     cy - radiusOut,
      cx - Cos60 * radiusOut, cy - Sin60 * radiusOut,
      cx - Cos30 * radiusOut, cy - Sin30 * radiusOut,
      cx - radiusOut,         cy,
      cx - Cos30 * radiusOut, cy + Sin30 * radiusOut,
      cx - Cos60 * radiusOut, cy + Sin60 * radiusOut
    )
    new DoDeclign(array)
  }
}