/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._, reflect.ClassTag

/** Reference data layer for [[HCen]] hex tile structure. */
trait LayerHcRef[A <: AnyRef] extends Any
{ type KeyT <: HexStruct

  /** The backing [[Array]] for the data elements of [[HCen]] structure. */
  def arrayUnsafe: Array[A]

  /** Apply method returns a data element from this data layer for the given [[HCen]]. The appropriate index is found from the implicit [[HGridSys]].
   * There is an alternative nme overload where the [[HGridSys]] is passed explicitly as the first parameter. */
  def apply(hc: HCen)(implicit key: KeyT): A = arrayUnsafe(key.layerArrayIndex(hc))

  /** Apply method returns a data element from this data layer for the given [[HCen]]. */
  def apply(key: KeyT, hc: HCen): A = arrayUnsafe(key.layerArrayIndex(hc))

  def rc(r: Int, c: Int)(implicit key: KeyT): A = arrayUnsafe(key.layerArrayIndex(r, c))

  def rc(key: KeyT, r: Int, c: Int): A = arrayUnsafe(key.layerArrayIndex(r, c))

  def set(hc: HCen, value: A)(implicit gridSys: HexStruct): Unit = { arrayUnsafe(gridSys.layerArrayIndex(hc)) = value }
  def set(r: Int, c: Int, value: A)(implicit gridSys: HexStruct): Unit = { arrayUnsafe(gridSys.layerArrayIndex(r, c)) = value }
}

/** Reference data layer for [[HCenRow]]. */
class LayerHcRefRow[A <: AnyRef](val row: Int, val arrayUnsafe: Array[A]) extends LayerHcRef[A]
{ override type KeyT = HCenRow
}

object LayerHcRefRow
{
  def apply[A <: AnyRef](row: Int, elems: A*)(implicit ct: ClassTag[A]): LayerHcRefRow[A] =
  { val array = new Array[A](elems.length)
    elems.iForeach{(i, a) => array(i) = a }
    new LayerHcRefRow[A](row, array)
  }

  /** Implicit [[Show]] type class instances / evidence for [[LayerHcRefRow]]. */
  implicit def showEv[A <: AnyRef](implicit evA: Show[A]): Show1Repeat[Int, A, LayerHcRefRow[A]] =
    Show1ArrayRepeat[Int, A, LayerHcRefRow[A]]("HRow", "row", _.row, "values", lhr => lhr.arrayUnsafe)

  /** Implicit [[Unahow]] type class instances / evidence for [[LayerHcRefRow]]. */
  implicit def unshowEv[A <: AnyRef](implicit evA: Unshow[A], ct: ClassTag[A]): Unshow1Repeat[Int, A, LayerHcRefRow[A]] =
    Unshow1Repeat[Int, A, LayerHcRefRow[A]]("HRow", "row", "values",  (r, seq) => new LayerHcRefRow[A](r, seq.toArray))(Unshow.intSubset(_.isEven), evA)

  implicit def eqTEv[A <: AnyRef](implicit evA: EqT[A]): EqT[LayerHcRefRow[A]] = (lr1, lr2) => lr1.arrayUnsafe === lr2.arrayUnsafe
}

class HCenRowLayer[A <: AnyRef](val r: Int, val unsafeArray: Array[A])(implicit val show2: Show[A]) extends Tell2Repeat[Int, A]
{ override def typeStr: String = "HRow"
  /** Element 1 of this Tell2+ element product. */
  override def tell1: Int = r

  /** Element 2 of this Tell2+ element product. */
  override def tell2Foreach(f: A => Unit): Unit = unsafeArray.foreach(f)

  override def show1: Show[Int] = Show.intEv
}

object HCenRowLayer
{
  def apply[A <: AnyRef](row:Int, mems: A*)(implicit show2: Show[A], ct: ClassTag[A]): HCenRowLayer[A] = new HCenRowLayer[A](row, mems.toArray)

  /** [[Show]] type class instances / evidence for [[HCenRowLayer]] */
  implicit def showEv[A <: AnyRef]: ShowTell[HCenRowLayer[A]] = ShowTell[HCenRowLayer[A]]("HRow")

  //def unshowEv[A <: AnyRef] = Unshow2Re
}