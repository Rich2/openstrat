/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex

/** A [[HGrider]] data grid of optional tile data. This is specialised for OptRef[A]. The tileGrid can map the [[HCen]] coordinate of the tile to the
 *  index of the Arr. Hence most methods take an implicit [[HGrider]] hex grid parameter. */
class HCenOptDGrid[A <: AnyRef](val unsafeArr: Array[A]) extends AnyVal with TCenOptDGrid[A]
{
  def clone: HCenOptDGrid[A] = new HCenOptDGrid[A](unsafeArr.clone)

  /** Sets the Some value of the hex tile data at the specified row and column coordinate values. This is an imperative mutating operation. */
  def unsafeSetSome(r: Int, c: Int, value: A)(implicit grider: HGrider): Unit = unsafeArr(grider.arrIndex(r, c)) = value

  /** Sets the Some value of the hex tile data at the specified [[HCen]] coordinate. This is an imperative mutating operation. */
  def unsafeSetSome(hc: HCen, value: A)(implicit grider: HGrider): Unit = unsafeArr(grider.arrIndex(hc)) = value

  /** Sets the Some values of the hex tile data at the specified row and column coordinate values. This is an imperative mutating operation. */
  def unsafeSetSomes(triples: (Int, Int, A)*)(implicit grider: HGrider): Unit = triples.foreach(t => unsafeArr(grider.arrIndex(t._1, t._2)) = t._3)

  /** Mutates the value ot the specified location to None. */
  def unsafeSetNone(hc: HCen)(implicit grider: HGrider): Unit = unsafeArr(grider.arrIndex(hc)) = null.asInstanceOf[A]

  def unsafeSetAll(value: A): Unit = iUntilForeach(0, length)(unsafeArr(_) = value)

  /** Creates a new ArrOpt with the specified location set to the specified value. */
  def setSome(hc: HCen, value: A)(implicit grider: HGrider): HCenOptDGrid[A] =
  { val newArr = unsafeArr.clone()
    newArr(grider.arrIndex(hc)) = value
    new HCenOptDGrid[A](newArr)
  }

  /** Creates a new ArrOpt with the specified location set to NoRef. */
  def setNone(hc: HCen)(implicit grider: HGrider): HCenOptDGrid[A] =
  { val newArr = unsafeArr.clone()
    newArr(grider.arrIndex(hc)) = null.asInstanceOf[A]
    new HCenOptDGrid[A](newArr)
  }

  /** Moves the object in the array location given by the 1st [[HCen]] to the 2nd [[HCen]], by setting hc2 to the value of hc1 and setting hc1 to
   *  None. */
  def unsafeMove(hc1: HCen, hc2: HCen)(implicit grider: HGrider): Unit =
  { unsafeArr(grider.arrIndex(hc2)) = unsafeArr(grider.arrIndex(hc1))
    unsafeArr(grider.arrIndex(hc1)) = null.asInstanceOf[A]
  }

  /** coordinate-foreach-Some. Foreach Some element and its associated [[HCen]] coordinate applies the side effecting parameter function. It ignores
   *  the None values. */
  def hcSomesForeach(f: (HCen, A) => Unit)(implicit grider: HGrider): Unit = grider.foreach { hc =>
    val a = unsafeArr(grider.arrIndex(hc))
    if (a != null) f(hc, a)
  }

  /** Coordinate-map. Maps the this Arr of Opt values, with their respective [[HCen]] coordinates to an Arr of type B. */
  def mapHCen[B, ArrT <: SeqImut[B]](fNone: => HCen => B)(fSome: (HCen, A) => B)(implicit grider: HGrider, build: ArrBuilder[B, ArrT]): ArrT =
  { val buff = build.newBuff()
    grider.foreach { hc =>
      val a = unsafeArr(grider.arrIndex(hc))
      if (a != null) build.buffGrow(buff, fSome(hc, a))
      else { val newVal = fNone(hc); build.buffGrow(buff, newVal) }
    }
    build.buffToBB(buff)
  }

  /** Indexes in to this [[HCenOptDGrid]] using the tile centre coordinate, either passed as an [[HCen]] or as row and column [[Int values]]. */
  def apply(hc: HCen)(implicit grider: HGrider): Option[A] =
  { if (!grider.hCenExists(hc)) None else
      { val elem = unsafeArr(grider.arrIndex(hc))
        if (elem == null) None else Some(elem)
      }
  }

  /** Indexes in to this [[HCenOptDGrid]] using the tile centre coordinate, either passed as an [[HCen]] or as row and column [[Int values]]. */
  def apply(r: Int, c: Int)(implicit grider: HGrider): Option[A] = {
    if (!grider.hCenExists(r, c)) None else {
      val elem = unsafeArr(grider.arrIndex(r, c))
      if (elem == null) None else Some(elem)
    }
  }

  /** Accesses element from Refs Arr. Only use this method where you are certain it is not null, or the consumer can deal with the null. */
  def unSafeApply(hc: HCen)(implicit grider: HGrider): A = unsafeArr(grider.arrIndex(hc))

  /** The tile is a None at the given hex grid centre coordinate [[HCen]]. */
  def tileNone(hc: HCen)(implicit grider: HGrider): Boolean = unsafeArr(grider.arrIndex(hc)) == null


  /** [[HCen]] with Some map. map the Some values of this HcenArrOpt, with the respective [[HCen]] coordinate to type B, the first type parameter B.
   *  Returns an immutable Array based collection of type ArrT, the second type parameter. */
  def hcSomesMap[B, ArrB <: SeqImut[B]](f: (HCen, A) => B)(implicit grider: HGrider, build: ArrBuilder[B, ArrB]): ArrB =
  { val buff = build.newBuff()

    grider.foreach { hc =>
      val a: A = unsafeArr(grider.arrIndex(hc))
      if(a != null)
      { val newVal = f(hc, a)
        build.buffGrow(buff, newVal)
      }
    }
    build.buffToBB(buff)
  }

  /** Maps the Somes of this [[HCenOptDGrid]] and the Some values of a second HCenArrOpt. Returns an immutable Array based collection of type ArrC, the
   *  second type parameter. */
  def some2sMap[B <: AnyRef, C, ArrC <: SeqImut[C]](optArrB: HCenOptDGrid[B])(f: (A, B) => C)(implicit grider: HGrider, build: ArrBuilder[C, ArrC]): ArrC =
  { val buff = build.newBuff()

    grider.foreach { hc =>
      val a: A = unsafeArr(grider.arrIndex(hc))
      val b: B = optArrB.unsafeArr(grider.arrIndex(hc))
      if(a != null & b != null)
      { val newVal = f(a, b)
        build.buffGrow(buff, newVal)
      }
    }
    build.buffToBB(buff)
  }

  /** [[HCen]] with Some values from 2 [[HCenArrOpts]] map to type C. This only maps the values where both collections have Some values. Returns an
   *  immutable Array based collection of type ArrC, the second type parameter. */
  def hcSome2sMap[B <: AnyRef, C, ArrC <: SeqImut[C]](optArrB: HCenOptDGrid[B])(f: (HCen, A, B) => C)(implicit grider: HGrider, build: ArrBuilder[C, ArrC]):
    ArrC =
  { val buff = build.newBuff()

    grider.foreach { hc =>
      val a: A = unsafeArr(grider.arrIndex(hc))
      val b: B = optArrB.unsafeArr(grider.arrIndex(hc))
      if(a != null)
      { val newVal = f(hc, a, b)
        build.buffGrow(buff, newVal)
      }
    }
    build.buffToBB(buff)
  }

  /** maps the [[HCen]] Coordinate values to type B, but only where the element is a None value. Returns an immutable Array based collection of type
   *  ArrB, the second type parameter. */
  def hcNonesMap[B, ArrB <: SeqImut[B]](f: HCen => B)(implicit grider: HGrider, build: ArrBuilder[B, ArrB]): ArrB =
  { val buff = build.newBuff()

    grider.foreach { r =>
      val a: A = unsafeArr(grider.arrIndex(r))
      if(a == null)
      { val newVal = f(r)
        build.buffGrow(buff, newVal)
      }
    }
    build.buffToBB(buff)
  }

  /** [[HCen]] with element flatMap, but only coordinates where there some value. Maps and flattens each [[HCen]] coordinate with its associated
   * element of type A. It ignores the None values. Note the function signature follows the foreach based convention of putting the collection element
   * 2nd or last as seen for example in fold methods' (accumulator, element) => B signature. */
  def hcSomesFlatMap[ArrT <: SeqImut[_]](f: (HCen, A) => ArrT)(implicit grider: HGrider, build: ArrFlatBuilder[ArrT]): ArrT =
  {
    val buff = build.newBuff()
    grider.foreach { hc =>
      val a = unsafeArr(grider.arrIndex(hc))
      if(a != null)
      { val newVal = f(hc, a)
        build.buffGrowArr(buff, newVal)
      }
    }
    build.buffToBB(buff)
  }

  def keyMap(implicit grider: HGrider): Map[A, HCen] =
  { val build = Map.newBuilder[A, HCen]
    hcSomesForeach((p, hc) => build.addOne(hc, p))
    build.result
  }

  def find(value: A)(implicit grider: HGrider): Option[HCen] =
  { var res: Option[HCen] = None
    hcSomesForeach{ (hc, a) => if (value == a) res = Some(hc)}
    res
  }
}