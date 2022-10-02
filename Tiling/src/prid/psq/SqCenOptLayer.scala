/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package psq

/** A [[SqGridSys]] square gird system of immutable optional [[SqCen]] tile data for a specific square tile grid [[SqGrid]]. This is specialised for
 *  OptRef[A]. The tileGrid can map the [[SqCen]] coordinate of the tile to the index of the Arr. Hence most methods take an implicit [[SqGrid]]
 *  square grid parameter. */
class SqCenOptLayer[A <: AnyRef](val unsafeArr: Array[A]) extends AnyVal with TCenOptDGrid[A]
{
  def clone: SqCenOptLayer[A] = new SqCenOptLayer[A](unsafeArr.clone)

  /** Sets the Some value of the square tile data at the specified row and column coordinate values. This is an imperative mutating operation. */
  def unsafeSetSome(r: Int, c: Int, value: A)(implicit gSys: SqGridSys): Unit = unsafeArr(gSys.arrIndex(r, c)) = value

  /** Sets the Some value of the hex tile data at the specified [[SqCen]] coordinate. This is an imperative mutating operation. */
  def unsafeSetSome(sc: SqCen, value: A)(implicit gSys: SqGridSys): Unit = unsafeArr(gSys.arrIndex(sc)) = value

  /** Sets the Some values of the hex tile data at the specified row and column coordinate values. This is an imperative mutating operation. */
  def unsafeSetSomes(triples: (Int, Int, A)*)(implicit gSys: SqGridSys): Unit = triples.foreach(t => unsafeArr(gSys.arrIndex(t._1, t._2)) = t._3)

  /** Creates a new ArrOpt with the specified location set to the specified value. */
  def setSome(r: Int, c: Int, value: A)(implicit gSys: SqGridSys): SqCenOptLayer[A] = setSome(SqCen(r, c), value)

  /** Creates a new ArrOpt with the specified location set to the specified value. */
  def setSome(sc: SqCen, value: A)(implicit gSys: SqGridSys): SqCenOptLayer[A] =
  { val newArr = unsafeArr.clone()
    newArr(gSys.arrIndex(sc)) = value
    new SqCenOptLayer[A](newArr)
  }

  /** Creates a new ArrOpt with the specified location set to NoRef. */
  def setNone(hc: SqCen)(implicit gSys: SqGridSys): SqCenOptLayer[A] =
  { val newArr = unsafeArr.clone()
    newArr(gSys.arrIndex(hc)) = null.asInstanceOf[A]
    new SqCenOptLayer[A](newArr)
  }

  /** The tile is a None at the given hex grid centre coordinate [[HCen]]. */
  def tileNone(sc: SqCen)(implicit gSys: SqGridSys): Boolean = unsafeArr(gSys.arrIndex(sc)) == null

  /** Accesses element from Refs Arr. Only use this method where you are certain it is not null, or the consumer can deal with the null. */
  def unSafeApply(sc: SqCen)(implicit gSys: SqGridSys): A = unsafeArr(gSys.arrIndex(sc))

  /** [[SqCen]] with Some map. map the Some values of this HcenArrOpt, with the respective [[SqCen]] coordinate to type B, the first type parameter B.
   *  Returns an immutable Array based collection of type ArrT, the second type parameter. */
  def scSomesMap[B, ArrB <: SeqImut[B]](f: (SqCen, A) => B)(implicit gSys: SqGridSys, build: ArrBuilder[B, ArrB]): ArrB =
  { val buff = build.newBuff()

    gSys.foreach { sc =>
      val a: A = unsafeArr(gSys.arrIndex(sc))
      if(a != null)
      { val newVal = f(sc, a)
        build.buffGrow(buff, newVal)
      }
    }
    build.buffToBB(buff)
  }


  /** Coordinate map Nones. Map the None values respective [[SqCen]] coordinates of this [[SqCenOptLayer]] to type B, the first type parameter. Returns
   *  an immutable Array based collection of type ArrT, the second type parameter. */
  def cMapNones[B, ArrT <: SeqImut[B]](f: SqCen => B)(implicit gSys: SqGridSys, build: ArrBuilder[B, ArrT]): ArrT =
  {
    val buff = build.newBuff()
    gSys.foreach { r =>
      val a: A = unsafeArr(gSys.arrIndex(r))
      if(a == null)
      { val newVal = f(r)
        build.buffGrow(buff, newVal)
      }
    }
    build.buffToBB(buff)
  }

  /** Moves the object in the array location given by HCen1 to HCen2, by setting H2 to the value of h1 and setting H1 to null. */
  def mutMove(s1: SqCen, s2: SqCen)(implicit gSys: SqGridSys): Unit =
  { unsafeArr(gSys.arrIndex(s2)) = unsafeArr(gSys.arrIndex(s1))
    unsafeArr(gSys.arrIndex(s1)) = null.asInstanceOf[A]
  }

  /** coordinate-foreach-Some. Foreach Some element and its associated [[HCen]] coordinate applies the side effecting parameter function. It ignores
   *  the None values. */
  def scSomesForeach(f: (SqCen, A) => Unit)(implicit gSys: SqGridSys): Unit = gSys.foreach { sc =>
    val a = unsafeArr(gSys.arrIndex(sc))
    if (a != null) f(sc, a)
  }

  /** Maps the Somes of this [[HCenArrOpt]] and the Some values of a second [[SqCenOptLayer]]. Returns an immutable Array based collection of type
   *  ArrC, the second type parameter. */
  def some2sMap[B <: AnyRef, C, ArrC <: SeqImut[C]](optArrB: SqCenOptLayer[B])(f: (A, B) => C)(implicit gSys: SqGridSys, build: ArrBuilder[C, ArrC]): ArrC =
  { val buff = build.newBuff()

    gSys.foreach { hc =>
      val a: A = unsafeArr(gSys.arrIndex(hc))
      val b: B = optArrB.unsafeArr(gSys.arrIndex(hc))
      if(a != null & b != null)
      { val newVal = f(a, b)
        build.buffGrow(buff, newVal)
      }
    }
    build.buffToBB(buff)
  }

  def keyMap(implicit grid: SqGrid): Map[A, SqCen] =
  { val build = Map.newBuilder[A, SqCen]
    scSomesForeach((p, hc) => build.addOne(hc, p))
    build.result
  }

  /** Moves the object in the array location given by the 1st [[SqCen]] to the 2nd [[SqCen]], by setting sc2 to the value of sc1 and setting sc1 to
   *  None. */
  def unsafeMove(sc1: SqCen, sc2: SqCen)(implicit gSys: SqGridSys): Unit =
  { unsafeArr(gSys.arrIndex(sc2)) = unsafeArr(gSys.arrIndex(sc1))
    unsafeArr(gSys.arrIndex(sc1)) = null.asInstanceOf[A]
  }
}