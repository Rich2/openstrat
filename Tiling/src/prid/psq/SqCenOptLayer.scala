/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package psq
import geom._

/** A layer of immutable optional [[SqCen]] data for a [[SqGridSys]] square grid system, This is specialised for OptRef[A]. The tileGrid can map the
 *  [[SqCen]] coordinate of the tile to the index of the Arr. Hence most methods take an implicit [[SqGridSys]] square grid system parameter. */
class SqCenOptLayer[A <: AnyRef](val unsafeArr: Array[A]) extends AnyVal with TCenOptDGrid[A]
{
  def clone: SqCenOptLayer[A] = new SqCenOptLayer[A](unsafeArr.clone)

  /** Sets the Some value of the square tile data at the specified row and column coordinate values. This is an imperative mutating operation. */
  def unsafeSetSome(r: Int, c: Int, value: A)(implicit gridSys: SqGridSys): Unit = unsafeArr(gridSys.arrIndex(r, c)) = value

  /** Sets the Some value of the hex tile data at the specified [[SqCen]] coordinate. This is an imperative mutating operation. */
  def unsafeSetSome(sc: SqCen, value: A)(implicit gridSys: SqGridSys): Unit = unsafeArr(gridSys.arrIndex(sc)) = value

  /** Sets the Some values of the hex tile data at the specified row and column coordinate values. This is an imperative mutating operation. */
  def unsafeSetSomes(triples: (Int, Int, A)*)(implicit gridSys: SqGridSys): Unit = triples.foreach(t => unsafeArr(gridSys.arrIndex(t._1, t._2)) = t._3)

  /** Creates a new ArrOpt with the specified location set to the specified value. */
  def setSome(r: Int, c: Int, value: A)(implicit gridSys: SqGridSys): SqCenOptLayer[A] = setSome(SqCen(r, c), value)

  /** Creates a new ArrOpt with the specified location set to the specified value. */
  def setSome(sc: SqCen, value: A)(implicit gridSys: SqGridSys): SqCenOptLayer[A] =
  { val newArr = unsafeArr.clone()
    newArr(gridSys.arrIndex(sc)) = value
    new SqCenOptLayer[A](newArr)
  }

  /** Creates a new ArrOpt with the specified location set to NoRef. */
  def setNone(hc: SqCen)(implicit gridSys: SqGridSys): SqCenOptLayer[A] =
  { val newArr = unsafeArr.clone()
    newArr(gridSys.arrIndex(hc)) = null.asInstanceOf[A]
    new SqCenOptLayer[A](newArr)
  }

  /** The tile is a None at the given hex grid centre coordinate [[HCen]]. */
  def tileNone(sc: SqCen)(implicit gridSys: SqGridSys): Boolean = unsafeArr(gridSys.arrIndex(sc)) == null

  /** Accesses element from Refs Arr. Only use this method where you are certain it is not null, or the consumer can deal with the null. */
  def unSafeApply(sc: SqCen)(implicit gridSys: SqGridSys): A = unsafeArr(gridSys.arrIndex(sc))

  /** [[SqCen]] with Some map. map the Some values of this HcenArrOpt, with the respective [[SqCen]] coordinate to type B, the first type parameter B.
   *  Returns an immutable Array based collection of type ArrT, the second type parameter. */
  def scSomesMap[B, ArrB <: SeqImut[B]](f: (SqCen, A) => B)(implicit gridSys: SqGridSys, build: ArrBuilder[B, ArrB]): ArrB =
  { val buff = build.newBuff()

    gridSys.foreach { sc =>
      val a: A = unsafeArr(gridSys.arrIndex(sc))
      if(a != null)
      { val newVal = f(sc, a)
        build.buffGrow(buff, newVal)
      }
    }
    build.buffToBB(buff)
  }

  /** Drops the None values mapping the [[Some]]'s value with the [[SqCen]] to an option value, collecting the values of the [[Some]]s returned by the
   * function. Returns a [[Seqimut]] of length 0 to the length of this [[SqCenOptLayer]]. */
  def projSomeScPtMap[B, ArrB <: SeqImut[B]](f: (A, SqCen, Pt2) => B)(implicit proj: SqSysProjection, build: ArrBuilder[B, ArrB]): ArrB =
    projSomeScPtMap(proj)(f)(build)

  def projSomeScPtMap[B, ArrB <: SeqImut[B]](proj: SqSysProjection)(f: (A, SqCen, Pt2) => B)(implicit build: ArrBuilder[B, ArrB]): ArrB =
  { val buff = build.newBuff()
    proj.gChild.foreach { sc =>
      val a: A = unsafeArr(proj.parent.arrIndex(sc))
      if (a != null) {
        build.buffGrow(buff, f(a, sc, proj.transCoord(sc)))
      }
    }
    build.buffToBB(buff)
  }

  /** Coordinate map Nones. Map the None values respective [[SqCen]] coordinates of this [[SqCenOptLayer]] to type B, the first type parameter. Returns
   *  an immutable Array based collection of type ArrT, the second type parameter. */
  def scNoneMap[B, ArrT <: SeqImut[B]](f: SqCen => B)(implicit gridSys: SqGridSys, build: ArrBuilder[B, ArrT]): ArrT =
  {
    val buff = build.newBuff()
    gridSys.foreach { r =>
      val a: A = unsafeArr(gridSys.arrIndex(r))
      if(a == null)
      { val newVal = f(r)
        build.buffGrow(buff, newVal)
      }
    }
    build.buffToBB(buff)
  }

  /** Coordinate map Nones. Map the None values respective [[SqCen]] coordinates of this [[SqCenOptLayer]] to type B, the first type parameter. Returns
   * an immutable Array based collection of type ArrT, the second type parameter. */
  def projNoneScPtMap[B, ArrB <: SeqImut[B]](f: (SqCen, Pt2) => B)(implicit proj: SqSysProjection, build: ArrBuilder[B, ArrB]): ArrB = projNoneScPtMap(proj)(f)

  /** Coordinate map Nones. Map the None values respective [[SqCen]] coordinates of this [[SqCenOptLayer]] to type B, the first type parameter. Returns
   * an immutable Array based collection of type ArrT, the second type parameter. */
  def projNoneScPtMap[B, ArrB <: SeqImut[B]](proj: SqSysProjection)(f: (SqCen, Pt2) => B)(implicit build: ArrBuilder[B, ArrB]): ArrB =
  { val buff = build.newBuff()
    proj.foreach { sc =>
      val a: A = unsafeArr(proj.parent.arrIndex(sc))
      if (a == null) {
        val newVal = f(sc, proj.transCoord(sc))
        build.buffGrow(buff, newVal)
      }
    }
    build.buffToBB(buff)
  }

  /** Moves the object in the array location given by HCen1 to HCen2, by setting H2 to the value of h1 and setting H1 to null. */
  def mutMove(s1: SqCen, s2: SqCen)(implicit gridSys: SqGridSys): Unit =
  { unsafeArr(gridSys.arrIndex(s2)) = unsafeArr(gridSys.arrIndex(s1))
    unsafeArr(gridSys.arrIndex(s1)) = null.asInstanceOf[A]
  }

  /** coordinate-foreach-Some. Foreach Some element and its associated [[SqCen]] coordinate applies the side effecting parameter function. It ignores
   *  the None values. */
  def someScForeach(f: (SqCen, A) => Unit)(implicit gridSys: SqGridSys): Unit = gridSys.foreach { sc =>
    val a = unsafeArr(gridSys.arrIndex(sc))
    if (a != null) f(sc, a)
  }

  /** Maps the Somes of this [[HCenArrOpt]] and the Some values of a second [[SqCenOptLayer]]. Returns an immutable Array based collection of type
   *  ArrC, the second type parameter. */
  def some2sMap[B <: AnyRef, C, ArrC <: SeqImut[C]](optArrB: SqCenOptLayer[B])(f: (A, B) => C)(implicit gridSys: SqGridSys, build: ArrBuilder[C, ArrC]): ArrC =
  { val buff = build.newBuff()

    gridSys.foreach { hc =>
      val a: A = unsafeArr(gridSys.arrIndex(hc))
      val b: B = optArrB.unsafeArr(gridSys.arrIndex(hc))
      if(a != null & b != null)
      { val newVal = f(a, b)
        build.buffGrow(buff, newVal)
      }
    }
    build.buffToBB(buff)
  }

  def keyMap(implicit gridSys: SqGridSys): Map[A, SqCen] =
  { val build = Map.newBuilder[A, SqCen]
    someScForeach((p, hc) => build.addOne(hc, p))
    build.result
  }

  /** Moves the object in the array location given by the 1st [[SqCen]] to the 2nd [[SqCen]], by setting sc2 to the value of sc1 and setting sc1 to
   *  None. */
  def unsafeMove(sc1: SqCen, sc2: SqCen)(implicit gridSys: SqGridSys): Unit =
  { unsafeArr(gridSys.arrIndex(sc2)) = unsafeArr(gridSys.arrIndex(sc1))
    unsafeArr(gridSys.arrIndex(sc1)) = null.asInstanceOf[A]
  }
}