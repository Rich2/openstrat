/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A square aligned to the X and Y axes. As this is a [[Polygon]] it is implemented using an [[Array]]. */
final class Sqlign private(val width: Double, override val cenX: Double, override val cenY: Double, val rtNum: Int, val clockwise: Boolean) extends Square, Rect, Tell2[Double, Pt2]//, PolygonLike[Pt2], Pt2SeqSpec
{ override type ThisT = Sqlign
  override def typeStr: String = "Sqlign"

  override def fromArray(array: Array[Double]): Sqlign = ??? // new Sqlign(array)
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
  override def slate(operand: VecPt2): Sqlign = Sqlign(width, cen.slate(operand))
  override def slate(xOperand: Double, yOperand: Double): Sqlign = Sqlign(width, cenX + xOperand, cenY + yOperand)
  override def slateX(xOperand: Double): Sqlign = Sqlign(width, cenX + xOperand, cenY)
  override def slateY(yOperand: Double): Sqlign = Sqlign(width, cenX, cenY + yOperand)
  override def scale(operand: Double): Sqlign = Sqlign(width * operand, cen.scale(operand))
  override def negX: Sqlign = Sqlign(width, -cenX, cenY)
  override def negY: Sqlign = Sqlign(width, cenX, -cenY)
  override def rotate90: Sqlign = Sqlign(width, cen.rotate90)
  override def rotate180: Sqlign = Sqlign(width, cen.rotate180)
  override def rotate270: Sqlign = Sqlign(width, cen.rotate270)
  override def prolign(matrix: ProlignMatrix): Sqlign = Sqlign(width * matrix.vFactor, cen.prolign(matrix))

  /** Adds a margin to this [[Sqlign]], square aligned with the XY axes, moving the sides out by the given parameter. */
  override def addMargin(delta: Double): Sqlign = Sqlign(width + 2 * delta, cenX, cenY)

  override def v0x: Double = rtNum match {
    case 0 => right
    case 1 if clockwise => right
    case 1 => left
    case 2 => left
    case 3 if clockwise => left
    case _ => right
  }
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

  override def arrayUnsafe: Array[Double] = rtNum match
  { case 0 if clockwise => Array[Double](right, top, right, bottom, left, bottom, left, top)
    case 0 => Array[Double](right, top, left, top, left, bottom, right, bottom)
    case 1 if clockwise => Array[Double](right, bottom, left, bottom, left, top, right, top)
    case 1 => Array[Double](right, bottom, right, top, left, top, left, bottom)
    case 2 if clockwise => Array[Double](left, bottom, left, top, right, top, right, bottom)
    case 2 => Array[Double](left, bottom, right, bottom, right, top, left, top)
    case 3 if clockwise => Array[Double](left, top, right, top, right, bottom, left, bottom)
    case _ => Array[Double](left, top, left, bottom, right, bottom, right, top)
  }
}

/** Companion object for [[Sqlign]] class, a square aligned to the X and Y axes. Contains factory apply methods. */
object Sqlign
{
  def apply(width: Double, cen: Pt2 = Pt2Z): Sqlign = new Sqlign(width, cen.x, cen.y, 0, true)
//  { val cx = cen.x
//    val cy = cen.y
//    val w = width / 2
//    val array = Array[Double](cx - w, cy + w, cx + w, cy + w, cx + w, cy - w, cx - w, cy - w)
//    new Sqlign(array)
//  }

  def apply(width: Double, cenX: Double, cenY: Double): Sqlign = new Sqlign(width, cenX, cenY, 0, true)
//  { val w = width / 2
//    val array = Array[Double](cenX - w, cenY + w, cenX + w, cenY + w, cenX + w, cenY - w, cenX - w, cenY - w)
//    new Sqlign(array)
//  }

  //def fromArray(array: Array[Double]) = new Sqlign(array)

  implicit val showEv: Show[Sqlign] = new Show[Sqlign]
  { override def typeStr: String = "Sqlign"
    override def strT(obj: Sqlign): String = obj.str
    override def show(obj: Sqlign, way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = obj.tell(way, maxPlaces, 0)
    override def syntaxDepth(obj: Sqlign): Int = 3
  }
}