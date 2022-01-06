/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid

/** Package ofr hex grids. */
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
}
