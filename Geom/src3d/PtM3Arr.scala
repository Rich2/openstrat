/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import collection.mutable.ArrayBuffer

/** Collection class for [[Pt3]]s. Only use this if the more specific [[PolygonM2]] and[[LinePathMs]] classes are not appropriate. */
class PtM3Arr(val unsafeArray: Array[Double]) extends AnyVal with ArrDbl3[PtM3]
{ type ThisT = PtM3Arr
  def fromArray(array: Array[Double]): ThisT = new PtM3Arr(array)
  override def typeStr: String = "Metres3s"
  override def fElemStr: PtM3 => String = _ => "Undefined" //_.str
  override def newElem(d1: Double, d2: Double, d3: Double): PtM3 = new PtM3(d1, d2, d3)

  /** This methods function is to work on a sequence of 3d points representing a polygon on the surface a globe (eg the Earth). If Z is positive its
   *  on the side of the Earth that the viewer is looking at. Returns z positive dist2 points if 1 or more of the points are z positive. Z negative
   *  points are moved to the horizon. */
  def earthZPositive: OptEither[PtM2Arr, CurveSegDists] =
  {
    existsCount(_.z.pos) match
    { case 0 => NoOptEither
    case n if n == length => SomeA(map(_.xy))
    case n => NoOptEither
      //      {
      //        var els: List[Either[Dist2, Dist2]] = lMap {
      //          case el if el.z.pos => Right(el.xy)
      //          case el =>
      //          { val xy = el.xy
      //            val fac = xy.magnitude / EarthAvRadius
      //            Left(xy / fac)
      //          }
      //        }
      //        while (els.head.isLeft && els.last.isLeft && els.init.last.isLeft) els = els.init
      //
      //        val els2: List[Either[Dist2, Dist2]] = els.drop(2).foldLeft(els.take(2))((acc, el) => el match
      //          {
      //            case Left(v) if acc.last.isLeft && acc.init.last.isLeft => acc.init :+ el
      //            case el => acc :+ el
      //          })
      //
      //        val acc: CurveSegDists = CurveSegDists.factory(els2.length)// List[CurveSegDist] = Nil
      //        var last: Either[Dist2, Dist2] = els2.last
      //        els2.iForeach {(e, i) =>
      //          e match
      //          { case Right(d2) => acc.setElem(i, LineSegDist(d2))
      //            case Left(d2) if last.isLeft => acc.setElem(i, ArcSegDist(Dist2Z, d2))
      //            case Left(d2) => acc.setElem(i, LineSegDist(d2))
      //          }
      //          last = e
      //        }
      //        GlobedSome(acc)
      //      }
    }
  }
}

object PtM3Arr extends Dbl3SeqLikeCompanion[PtM3, PtM3Arr]
{ override def fromArray(array: Array[Double]): PtM3Arr = new PtM3Arr(array)

  implicit val arrFlatBuilderImplicit: Dbl3ArrFlatBuilder[PtM3Arr] = new Dbl3ArrFlatBuilder[PtM3Arr]
  { type BuffT = PtM3Buff
    override def fromDblArray(array: Array[Double]): PtM3Arr = new PtM3Arr(array)
    override def buffFromBufferDbl(inp: ArrayBuffer[Double]): PtM3Buff = new PtM3Buff(inp)
  }
}

/** A specialised flat ArrayBuffer[Double] based class for [[Pt3]]s collections. */
final class PtM3Buff(val unsafeBuffer: ArrayBuffer[Double]) extends AnyVal with Dbl3Buff[PtM3]
{ override def typeStr: String = "BuffPtMetre3"
  def newElem(d1: Double, d2: Double, d3: Double): PtM3 = new PtM3(d1, d2, d3)
}

object PtM3Buff
{
  def apply(initSize: Int = 4): PtM3Buff = new PtM3Buff(new ArrayBuffer[Double](initSize * 3))
}