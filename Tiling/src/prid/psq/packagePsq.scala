/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid

/** Pacakge for square tile grids. */
package object psq
{
  /** The square centre step values. */
  val scSteps: Arr[SqStep] = Arr(SqStepUp, SqStepUR, SqStepRt, SqStepDR, SqStepDn, SqStepDL, SqStepLt, SqStepUL)

  implicit class ArrExtensions(thisArr: Arr[AnyRef])
  { /** Finds the first [[SqCen]] in this sequence */
    def sqCenFind: Option[SqCen] =
    {
      var res: Option[SqCen] = None
      var count = 0
      while (res.isEmpty & count < thisArr.length) thisArr(count) match {
        case sc: SqCen => res = Some(sc)
        case _ => count += 1
      }
      res
    }

    /** Finds the first [[SqCen]] in this sequence and performs the side effecting function on it. */
    def sqCenForFirst(f: SqCen => Unit): Unit =
    { var count = 0
      while (count < thisArr.length) thisArr(count) match
      { case sc: SqCen => { f(sc); count = thisArr.length }
        case _ => count += 1
      }
    }
  }

  implicit class AnysExtensions(thisArr: Anys)
  { /** Finds the first [[SqCen]] in this sequence */
    def sqCenFind: Option[SqCen] =
    {
      var res: Option[SqCen] = None
      var count = 0
      while (res.isEmpty & count < thisArr.length) thisArr(count) match {
        case sc: SqCen => res = Some(sc)
        case _ => count += 1
      }
      res
    }

    /** Finds the first [[SqCen]] in this sequence and performs the side effecting function on it. */
    def sqCenForFirst(f: SqCen => Unit): Unit =
    { var count = 0
      while (count < thisArr.length) thisArr(count) match
      { case sc: SqCen => { f(sc); count = thisArr.length }
        case _ => count += 1
      }
    }
  }
}