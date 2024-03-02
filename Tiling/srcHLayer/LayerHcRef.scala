/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import reflect.ClassTag

/** Reference data layer for [[HCen]] hex tile structure. */
trait LayerHcRef[A <: AnyRef] extends Any with LayerTcRef[A]
{ //type KeyT <: HexStruct

  /** Apply method returns a data element from this data layer for the given [[HCen]]. The appropriate index is found from the implicit [[HGridSys]].
   * There is an alternative nme overload where the [[HGridSys]] is passed explicitly as the first parameter. */
  def apply(hc: HCen)(implicit key: HexStruct): A = arrayUnsafe(key.layerArrayIndex(hc))

  /** Apply method returns a data element from this data layer for the given [[HCen]]. */
  def apply(key: HexStruct, hc: HCen): A = arrayUnsafe(key.layerArrayIndex(hc))

  def rc(r: Int, c: Int)(implicit key: HexStruct): A = arrayUnsafe(key.layerArrayIndex(r, c))

  def rc(key: HexStruct, r: Int, c: Int): A = arrayUnsafe(key.layerArrayIndex(r, c))

  def set(hc: HCen, value: A)(implicit gridSys: HexStruct): Unit = { arrayUnsafe(gridSys.layerArrayIndex(hc)) = value }
  def set(r: Int, c: Int, value: A)(implicit gridSys: HexStruct): Unit = { arrayUnsafe(gridSys.layerArrayIndex(r, c)) = value }

  /** [[HCen]] with foreach. Applies the side effecting function to the [[HCen]] coordinate with its respective element. Note the function signature
   *  follows the foreach based convention of putting the collection element 2nd or last as seen for example in fold methods' (accumulator, element)
   *  => B signature.  */
  def hcForeach[U](f: (HCen, A) => U)(implicit gSys: HexStruct): Unit = gSys.iForeach{ (i, hc) => f(hc, arrayUnsafe(i)); () }
  /** [[HCen]] with map. Applies the function to each [[HCen]] coordinate with the corresponding element in the underlying array. Note the function
   *  signature follows the foreach based convention of putting the collection element 2nd or last as seen for example in fold methods' (accumulator,
   *  element) => B signature. */
  def hcMap[B, BB <: Arr[B]](f: (HCen, A) => B)(implicit grid: HexStruct, build: BuilderArrMap[B, BB]): BB =
  { val res = build.uninitialised(length)
    grid.iForeach{ (i, hc) =>
      val newElem = f(hc, apply(hc))
      res.setElemUnsafe(i, newElem)
    }
    res
  }

  /** Maps each data element with thw corresponding [[HCen]] to an [[Option]] of type B. Collects the [[Some]]'s values. The length of the returned
   * [[Arr]] will be between 0 and the length of this [[LayerHcRefSys]]. */
  def hcOptMap[B, BB <: Arr[B]](f: (A, HCen) => Option[B])(implicit grid: HexStruct, build: BuilderArrMap[B, BB]): BB =
  { val buff = build.newBuff()
    grid.iForeach { (i, hc) =>
      f(apply(hc), hc).foreach(build.buffGrow(buff, _))
    }
    build.buffToSeqLike(buff)
  }

  /** [[HCen]] with flatmap. Applies the function to each [[HCen]] coordinate with the corresponding element in the underlying array. Note the
   *  function signature follows the foreach based convention of putting the collection element 2nd or last as seen for example in fold methods' (accumulator,
   *  element) => B signature. */
  def hcFlatMap[BB <: Arr[?]](f: (HCen, A) => BB)(implicit grid: HexStruct, build: BuilderArrFlat[BB]): BB =
  { val buff = build.newBuff()
    grid.iForeach{ (i, hc) =>
      val newElems = f(hc, apply(hc))
      build.buffGrowArr(buff, newElems)
    }
    build.buffToSeqLike(buff)
  }

  /** [[HCen]] with optFlatmap. Applies the function to each [[HCen]] coordinate with the corresponding element in the underlying array. Note the
   * function signature follows the foreach based convention of putting the collection element 2nd or last as seen for example in fold methods' (accumulator,
   * element) => B signature. */
  def hcOptFlatMap[BB <: Arr[?]](f: (HCen, A) => Option[BB])(implicit gridSys: HexStruct, build: BuilderArrFlat[BB]): BB =
  { val buff = build.newBuff()
    gridSys.iForeach { (i, hc) =>
      f(hc, apply(hc)).foreach(build.buffGrowArr(buff, _))
    }
    build.buffToSeqLike(buff)
  }

  def projHCenFlatMap[BB <: Arr[?]](f: (HCen, A) => BB)(implicit key: HexStruct, proj: HSysProjection, build: BuilderArrFlat[BB]): BB =
    proj.hCenFlatMap{ hc => f(hc, apply(hc)(key)) }
}

/** Reference data layer for [[HCenRow]]. */
class LayerHcRefRow[A <: AnyRef](val row: Int, val arrayUnsafe: Array[A]) extends LayerHcRef[A]
{ //override type KeyT = HCenRow
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