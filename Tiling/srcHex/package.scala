/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid

/** Package for hex grids. */
package object phex
{
  /** The hex centre step values. */
  val hcSteps: RArr[HStep] = RArr(HexUR, HexRt, HexDR, HexDL, HexLt, HexUL)

  def cScaleToArea(a: Double): Double = 8.0 * 3.sqrt * a.squared

  def tileScaleToArea(a: Double): Double = 3.sqrt * a.squared / 2.0
  /** phex package extensions for Int. */
  implicit class IntGridImplicit(thisInt: Int)
  { /** Syntax for succinct [[HCen]] notation. */
    def hc (c: Int): HCen = HCen(thisInt, c)

    /** Syntax for succinct [[HSep]] notation. */
    def hs (c: Int): HSep = HSep(thisInt, c)

    /** Syntax for succinct [[HVert]] notation. */
    def hv (c: Int): HVert = HVert(thisInt, c)
  }

  implicit class RArrAnysExtensions(thisArr: RArr[Any])
  { /** Finds the first [[HCen]] in this sequence */
    def hCenFind: Option[HCen] =
    { var res: Option[HCen] = None
      var count = 0
      while (res.isEmpty & count < thisArr.length) thisArr(count) match
      { case hc: HCen => res = Some(hc)
        case _ => count += 1
      }
      res
    }

    /** Finds the first [[HCen]] in this sequence and performs the side effecting function on it. */
    def findHCenForEach(f: HCen => Unit): Unit =
    { var count = 0
      while (count < thisArr.length) thisArr(count) match
      { case hc: HCen =>
        { f(hc)
          count = thisArr.length
        }
        case _ => count += 1
      }
    }
  }
}