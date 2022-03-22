/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** Regular Hexagon where two of the sides are parallel to the X Axis */
final class HexParrX(val unsafeArray: Array[Double]) extends Hexlign with Show2[Double, Pt2]
{ override type ThisT = HexParrX
  override def unsafeFromArray(array: Array[Double]): HexParrX = new HexParrX(array)
  def height: Double = ???
  //, val cenX: Double, val cenY: Double
  override def typeStr = "HexXlign"
  override def name1: String = "height"
  override def name2: String = "cen"
  override def diameterIn: Double = height
  override def width: Double = diameterOut
  override def show1: Double = height
  override def show2: Pt2 = cen
  override implicit def showT1: ShowT[Double] = ShowT.doublePersistEv
  override implicit def showT2: ShowT[Pt2] = Pt2.persistImplicit
  override def syntaxDepth: Int = 3

  override def cen: Pt2 = cenX pp cenY

  /** Translate 2D geometric transformation on this HexXlign returns a HexXlign. */
  override def slateXY(xDelta: Double, yDelta: Double): HexParrX = HexParrX(diameterIn, cen.addXY(xDelta, yDelta))

  /** Uniform scaling against both X and Y axes 2D geometric transformation on this HexXlign returning a HexXlign. */
  override def scale(operand: Double): HexParrX = HexParrX(diameterIn * operand, cen.scale(operand))

  /** Mirror, reflection 2D geometric transformation on this HexXlign across the X axis, negates Y, returns a HexXlign. */
  override def negY: HexParrX = HexParrX(diameterIn, cen.negY)

  /** Mirror, reflection 2D transformation on this HexXlign across the Y axis, negates X, returns a HexXlign. */
  override def negX: HexParrX = HexParrX(diameterIn, cen.negX)

  /** Rotate 90 degrees in a positive or clockwise direction 2D geometric transformation on this HexXlign across the Y axis, negates X, returns a
   *  HexYlign. Note the change in type. Equivalent to a 270 degree negative or clock wise transformation. */
  override def rotate90: HexParrY = HexParrY(diameterIn, cen.rotate90)

  /** Rotate 180 degrees 2D geometric transformation on this HexXlign across the Y axis, negates X, returns a HexXlign. */
  override def rotate180: HexParrX = HexParrX(diameterIn, cen.rotate180)

  /** Rotate 270 degrees in a positive or clockwise direction 2D geometric transformation on this HexXlign across the Y axis, negates X, returns a
   *  HexYlign. Note the change in type. Equivalent to a 90 degree negative or clock wise transformation. */
  override def rotate270: HexParrY = HexParrY(diameterIn, cen.rotate270)

  /** Prolign 2d geometric transformations, similar transformations that retain alignment with the axes on this HexXlign returns a HexXlign. */
  override def prolign(matrix: ProlignMatrix): HexParrX = HexParrX(diameterIn, cen.prolign(matrix))
}

/** Companion object for the regular hexagon aligned to the X Axis class. It has a limited set of 2D geometric transformation type class instances as
 * the type can not be maintained through all affine transformations. */
object HexParrX
{ /** Apply factory method for HexXlign, Creates a regular hexagon with 2 of its side aligned to the X axis. */
  def apply(height: Double, cen: Pt2 = Pt2Z): HexParrX = ???//new HexParrX(height, cen.x, cen.y)

  /** Apply factory method for [[HexParrX]], Creates a regular hexagon with 2 of its side aligned to the Y axis. */
  def apply(height: Double, xCen: Double, yCen: Double): HexParrX = ???//new HexParrX(height, xCen, yCen)

  /*override def v4x: Double = cenX - radiusOut
  override def v4y: Double = cenY
  @inline override def v4: Pt2 = Pt2(v4x, v4y)*/

  /*override def v5x: Double = cenX - radiusOut / 2
  override def v5y: Double = cenY + radiusIn
  @inline override def v5: Pt2 = Pt2(v5x, v5y)*/

  def unapply(input: HexParrX): Some[(Double, Pt2)] = Some((input.height, input.cen))

  implicit val persistImplicit: Persist[HexParrX] = Persist2[Double, Pt2, HexParrX]("HexXlign", "height", _.height,"cen", _.cen, apply)
  implicit val slateImplicit: Slate[HexParrX] = (obj: HexParrX, dx: Double, dy: Double) => obj.slateXY(dx, dy)
  implicit val scaleImplicit: Scale[HexParrX] = (obj: HexParrX, operand: Double) => obj.scale(operand)
  implicit val prolignImplicit: Prolign[HexParrX] = (obj, matrix) => obj.prolign(matrix)
}