/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package psq
import geom._

/** A view of a square grid, currently representing the [[SqCoord]] focus and the pixels/dx scale. */
class SGView(val r: Int, val c: Int, val pixelsPerC: Double) extends Tell2[SqCoord, Double]
{ def hCoord: SqCoord = SqCoord(r, c)
  def vec: Vec2 = hCoord.toVecReg
  def pt2: Pt2 = hCoord.toPt2Reg
  override def typeStr: String = "GridView"
  override def name1: String = "hCoord"
  inline override def show1: SqCoord = hCoord
  override def name2: String = "pxScale"
  inline override def show2: Double = pixelsPerC
  override implicit def persist1: Show[SqCoord] = SqCoord.persistImplicit
  override implicit def persist2: Show[Double] = Show.doublePersistEv
  override def syntaxDepth: Int = 3
}

/** Companion object for [[SGView]] class. Contains factory apply method overloads and implicit Persist instance. */
object SGView
{ def apply(r: Int, c: Int, pxScale: Double = 50): SGView = new SGView(r, c, pxScale)
  def apply(sqCoord: SqCoord, pxScale: Double): SGView = new SGView(sqCoord.r, sqCoord.c, pxScale)

  /** Implicit [[Persist]] instance for SqGridView.  */
  implicit val persistImplicit: PersistTell2[SqCoord, Double, SGView] =
    PersistTell2[SqCoord, Double, SGView]("SqGridView", "sqCoord", "pxScale", apply(_, _))
}