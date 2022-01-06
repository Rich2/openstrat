/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid

/** Pacakge for square tile grids. */
package object psq
{
  /** The square centre step values. */
  val scSteps: Arr[SqStep] = Arr(SqStepUp, SqStepUR, SqStepRt, SqStepDR, SqStepDn, SqStepDL, SqStepLt, SqStepUL)
}