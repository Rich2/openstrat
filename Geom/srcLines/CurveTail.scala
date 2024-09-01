/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

trait CurveTail
{ /** The end point of the [[CurveSeg]]. */
  def endPt: Pt2
  
  /** The full [[CurveSeg]] */
  def curveSeg(startPt: Pt2): CurveSeg
}

/** A [[CurveTail]] that can be defined with a max of 8 [[Double]]s. */
trait CurveTailMax8 extends CurveTail

/** A [[CurveTail]] that can be defined with a max of 6 [[Double]]s. */
trait CurveTailMax6 extends CurveTailMax8
