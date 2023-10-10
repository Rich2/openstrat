/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** Regular Hexagon where two of the sides are parallel to the X Axis */
final class HexParrX(val unsafeArray: Array[Double]) extends Hexlign with Tell2[Double, Pt2]
{ override type ThisT = HexParrX
  override def fromArray(array: Array[Double]): HexParrX = new HexParrX(array)

  override def typeStr = "HexXlign"
  override def name1: String = "height"
  override def name2: String = "cen"
  def height: Double = (v1y - v2y).abs
  override def diameterIn: Double = (v1y - v2y).abs
  override def diameterOut: Double = v5.distTo(v2)
  override def width: Double = v5.distTo(v2)
  override def radiusIn: Double = diameterIn / 2
  override def radiusOut: Double = diameterOut / 2
  override def tell1: Double = height
  override def tell2: Pt2 = cen
  override implicit def persist1: Show[Double] = Show.doublePersistEv
  override implicit def persist2: Show[Pt2] = Pt2.persistImplicit
  override def syntaxDepth: Int = 3

  /** maps the vertices of this [[HexParrX]] to a new [[HexparrX]] instance. */
  override def vertsTrans(f: Pt2 => Pt2): HexParrX = HexParrX.fromArray(unsafeMap(f))

  /** Translate 2D geometric transformation on this HexYlign returns a HexYlign. */
  override def slate(delta: Vec2Like): HexParrX = vertsTrans(_.slate(delta))

  /** Translate 2D geometric transformation on this HexXlign returns a HexXlign. */
  override def slateXY(xDelta: Double, yDelta: Double): HexParrX = vertsTrans(_.addXY(xDelta, yDelta))

  /** Uniform scaling against both X and Y axes 2D geometric transformation on this HexXlign returning a HexXlign. */
  override def scale(operand: Double): HexParrX = vertsTrans(_.scale(operand))

  /** Mirror, reflection 2D geometric transformation on this HexXlign across the X axis, negates Y, returns a HexXlign. */
  override def negY: HexParrX = HexParrX.fromArray(unsafeNegY)

  /** Mirror, reflection 2D transformation on this HexXlign across the Y axis, negates X, returns a HexXlign. */
  override def negX: HexParrX = HexParrX.fromArray(unsafeNegX)

  /** Rotate 90 degrees in a positive or clockwise direction 2D geometric transformation on this HexXlign across the Y axis, negates X, returns a
   *  HexYlign. Note the change in type. Equivalent to a 270 degree negative or clock wise transformation. */
  override def rotate90: HexParrY = HexParrY.fromArray(unsafeMap(_.rotate90))

  /** Rotate 180 degrees 2D geometric transformation on this HexXlign across the Y axis, negates X, returns a HexXlign. */
  override def rotate180: HexParrX = vertsTrans(_.rotate180)

  /** Rotate 270 degrees in a positive or clockwise direction 2D geometric transformation on this HexXlign across the Y axis, negates X, returns a
   *  HexYlign. Note the change in type. Equivalent to a 90 degree negative or clock wise transformation. */
  override def rotate270: HexParrY = HexParrY.fromArray(unsafeMap(_.rotate270))

  /** Prolign 2d geometric transformations, similar transformations that retain alignment with the axes on this HexXlign returns a HexXlign. */
  override def prolign(matrix: ProlignMatrix): HexParrX = HexParrX(diameterIn, cen.prolign(matrix))
}

/** Companion object for the regular hexagon aligned to the X Axis class. It has a limited set of 2D geometric transformation type class instances as
 * the type can not be maintained through all affine transformations. */
object HexParrX
{ /** Apply factory method for HexXlign, Creates a regular hexagon with 2 of its side aligned to the X axis. */
  def apply(height: Double, cen: Pt2 = Pt2Z): HexParrX = apply(height, cen.x, cen.y)

  /** Apply factory method for [[HexParrX]], Creates a regular hexagon with 2 of its side aligned to the Y axis. */
  def apply(height: Double, xCen: Double, yCen: Double): HexParrX = new HexParrX(unsafe(height, xCen, yCen))

  def unsafe(height: Double, xCen: Double, yCen: Double): Array[Double] =
  {
    val h2: Double = height / 2
    val dsq3: Double = height / 3.sqrt
    val d2sq3: Double = height / (3.sqrt * 2)
    Array[Double](
      xCen + d2sq3, yCen + h2,
      xCen + dsq3, yCen,
      xCen + d2sq3, yCen - h2,
      xCen - d2sq3, yCen - h2,
      xCen - dsq3, yCen,
      xCen - d2sq3, yCen + h2
    )
  }

  def unapply(input: HexParrX): Some[(Double, Pt2)] = Some((input.height, input.cen))
  def fromArray(array: Array[Double]): HexParrX = new HexParrX(array)
  implicit val persistImplicit: Persist[HexParrX] = Persist2[Double, Pt2, HexParrX]("HexXlign", "height", _.height,"cen", _.cen, apply)
  implicit val slateImplicit: Slate[HexParrX] = (obj: HexParrX, dx: Double, dy: Double) => obj.slateXY(dx, dy)
  implicit val scaleImplicit: Scale[HexParrX] = (obj: HexParrX, operand: Double) => obj.scale(operand)
  implicit val prolignImplicit: Prolign[HexParrX] = (obj, matrix) => obj.prolign(matrix)
}