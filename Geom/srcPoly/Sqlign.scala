/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb._

/** A square aligned to the X and Y axes. */
final class Sqlign private(val unsafeArray: Array[Double]) extends Square with Rect with Tell2[Double, Pt2]
{ override type ThisT = Sqlign
  override def typeStr: String = "Sqlign"
  override def fromArray(array: Array[Double]): Sqlign = new Sqlign(array)
  override def vertsTrans(f: Pt2 => Pt2): Sqlign = Sqlign.fromArray(unsafeMap(f))
  def width: Double = v1x - v0x
  override def name1: String = "width"
  override def name2: String = "cen"
  override def tell1: Double = width
  override def tell2: Pt2 = cen
  override implicit def show1: Show[Double] = Show.doubleEv
  override implicit def show2: Show[Pt2] = Pt2.persistEv
  override def tellDepth: Int = 3
//  override def attribs: RArr[XmlAtt] = ???
  override def width1: Double = width
  override def width2: Double = width
  override def height: Double = width
  override def rotation: AngleVec = 0.degsVec
  override def slate(offset: Vec2Like): Sqlign = Sqlign(width, cen.slate(offset))

  override def slateXY(xDelta: Double, yDelta: Double): Sqlign = Sqlign(width, cenX + xDelta, cenY + yDelta)
  override def scale(operand: Double): Sqlign = Sqlign(width * operand, cen.scale(operand))

  override def negY: Sqlign = Sqlign(width, cenX, -cenY)

  override def negX: Sqlign = Sqlign(width, -cenX, cenY)

  override def rotate90: Sqlign = Sqlign(width, cen.rotate90)
  override def rotate180: Sqlign = Sqlign(width, cen.rotate180)
  override def rotate270: Sqlign = Sqlign(width, cen.rotate270)

  override def prolign(matrix: ProlignMatrix): Sqlign = Sqlign(width * matrix.vFactor, cen.prolign(matrix))

  /** Adds a margin to this [[Sqlign]], square aligned with the XY axes, moving the sides out by the given parameter. */
  override def addMargin(delta: Double): Sqlign = Sqlign(width + 2 * delta, cenX, cenY)
}

/** Companion object for [[Sqlign]] class, a square aligned to the X and Y axes. Contains factory apply methods. */
object Sqlign
{
  def apply(width: Double, cen: Pt2 = Pt2Z): Sqlign =
  { val cx = cen.x
    val cy = cen.y
    val w = width / 2
    val array = Array[Double](cx - w, cy + w, cx + w, cy + w, cx + w, cy - w, cx - w, cy - w)
    new Sqlign(array)
  }

  def apply(width: Double, cenX: Double, cenY: Double): Sqlign =
  { val w = width / 2
    val array = Array[Double](cenX - w, cenY + w, cenX + w, cenY + w, cenX + w, cenY - w, cenX - w, cenY - w)
    new Sqlign(array)
  }

  def fromArray(array: Array[Double]) = new Sqlign(array)

  implicit val ShowTImplicit: Show[Sqlign] = new Show[Sqlign]
  { override def typeStr: String = "Sqlign"
    override def strT(obj: Sqlign): String = obj.str
    override def show(obj: Sqlign, way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = obj.tell(way, maxPlaces, 0)
    override def syntaxDepth(obj: Sqlign): Int = 3
  }
}