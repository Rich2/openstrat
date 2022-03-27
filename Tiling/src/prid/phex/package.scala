/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid

/** Package for hex grids. */
package object phex
{
  /** The hex centre step values. */
  val hcSteps: Arr[HStep] = Arr(HStepUR, HStepRt, HStepDR, HStepDL, HStepLt, HStepUL)

  implicit class IntGridImplicit(thisInt: Int)
  { /** Syntax for succinct [[HCen]] notation. */
    def hc (c: Int): HCen = HCen(thisInt, c)

    /** Syntax for succinct [[HCen]] notation. */
    def hs (c: Int): HSide = HSide(thisInt, c)
  }

  implicit class ArrExtensions(thisArr: Arr[AnyRef])
  { /** Finds the first [[HCen]] in this sequence */
    def hCenFind: Option[HCen] =
    {
      var res: Option[HCen] = None
      var count = 0
      while (res.isEmpty & count < thisArr.length) thisArr(count) match {
        case hc: HCen => res = Some(hc)
        case _ => count += 1
      }
      res
    }

    /** Finds the first [[HCen]] in this sequence and performs the side effecting function on it. */
    def hCenForFirst(f: HCen => Unit): Unit =
    { var count = 0
      while (count < thisArr.length) thisArr(count) match
      { case hc: HCen => { f(hc); count = thisArr.length }
        case _ => count += 1
      }
    }
  }

  implicit class AnysExtensions(thisArr: Anys)
  { /** Finds the first [[HCen]] in this sequence */
    def hCenFind: Option[HCen] =
    {
      var res: Option[HCen] = None
      var count = 0
      while (res.isEmpty & count < thisArr.length) thisArr(count) match {
        case hc: HCen => res = Some(hc)
        case _ => count += 1
      }
      res
    }

    /** Finds the first [[HCen]] in this sequence and performs the side effecting function on it. */
    def findHCenForEach(f: HCen => Unit): Unit =
    { var count = 0
      while (count < thisArr.length) thisArr(count) match
      { case hc: HCen => { f(hc); count = thisArr.length }
        case _ => count += 1
      }
    }
  }
}