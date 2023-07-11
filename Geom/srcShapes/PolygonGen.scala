/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import Colour.Black, pWeb._

/** The implementation class for a general [[Polygon]] as opposed to a specific [[Polygon]] such as a [[Square]] or a [[Rectangle]], is encoded as a
 *  sequence of plain 2 dimension (mathematical) vectors. Minimum length 3. Clockwise is the default. Polygon may be altered to include a centre. */
final class PolygonGen(val unsafeArray: Array[Double]) extends Polygon with Pt2SeqLike with AffinePreserve with Dbl2SeqSpec[Pt2]
{ override type ThisT = PolygonGen

  override def fromArray(array: Array[Double]): PolygonGen = new PolygonGen(array)
  override def typeStr: String = "Polygon"
  override def vertsNum: Int = unsafeArray.length / 2
  override def fill(fillColour: Colour): PolygonFill = PolygonFill(this, fillColour)
  override def draw(lineColour: Colour = Black, lineWidth: Double = 2): PolygonDraw = PolygonDraw(this, lineWidth, lineColour)
  @inline override def polygonMap(f: Pt2 => Pt2): PolygonGen = vertsMap(f).toPolygon

  override def vertsTrans(f: Pt2 => Pt2): PolygonGen = new PolygonGen(arrTrans(f))

  /** A method to perform all the [[ProlignPreserve]] transformations with a function from PT2 => PT2. */
  @inline override def ptsTrans(f: Pt2 => Pt2): PolygonGen = vertsTrans(f)

  override def attribs: RArr[XAttNumeric] = ???

  override def canEqual(that: Any): Boolean = that match {
    case s: Shape => true
    case _ => false
  }

  def eq(obj: PolygonGen): Boolean = unsafeArray.sameElements(obj.unsafeArray)
  def minX: Double = ssTailFold(v0.x)((acc, el) => acc.min(el.x))
  def maxX: Double = ssTailFold(v0.x)((acc, el) => acc.max(el.x))
  def minY: Double = ssTailFold(v0.y)((acc, el) => acc.min(el.y))
  def maxY: Double = ssTailFold(v0.y)((acc, el) => acc.max(el.y))
  def width: Double = maxX - minX
  def height: Double = maxY - minY

  /** Insert vertex. */
  override def insVert(insertionPoint: Int, newVec: Pt2): PolygonGen =
  { val res = PolygonGen.uninitialised(ssLength + 1)
    (0 until insertionPoint).foreach(i => res.setElemUnsafe(i, ssIndex(i)))
    res.setElemUnsafe(insertionPoint, newVec)
    (insertionPoint until ssLength).foreach(i => res.setElemUnsafe(i + 1, ssIndex(i)))
    res
  }

  /** Insert vertices */
  override def insVerts(insertionPoint: Int, newVecs: Pt2 *): PolygonGen =
  { val res = PolygonGen.uninitialised(ssLength + newVecs.length)
    (0 until insertionPoint).foreach(i => res.setElemUnsafe(i, ssIndex(i)))
    newVecs.iForeach((i, elem) => res.setElemUnsafe(insertionPoint + i, elem))
    (insertionPoint until ssLength).foreach(i => res.setElemUnsafe(i + newVecs.length, ssIndex(i)))
    res
  }

  def distScale(distRatio: Length): PolygonM2 = map[PtM2, PolygonM2](p => p.toMetres(distRatio))
}

/** Companion object for [[PolygonGen]]. */
object PolygonGen extends Dbl2SeqLikeCompanion[Pt2, PolygonGen]
{ override def fromArray(array: Array[Double]): PolygonGen = new PolygonGen(array)

  implicit val eqImplicit: EqT[PolygonGen] = (p1, p2) => EqT.arrayImplicit[Double].eqT(p1.unsafeArray, p2.unsafeArray)

  implicit val persistImplicit: Dbl2SeqDefPersist[Pt2, PolygonGen] = new Dbl2SeqDefPersist[Pt2, PolygonGen]("Polygon")
  { override def fromArray(value: Array[Double]): PolygonGen = new PolygonGen(value)
    override def showDecT(obj: PolygonGen, way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = ???
  }
}