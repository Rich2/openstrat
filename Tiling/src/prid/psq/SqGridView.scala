/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package psq
import geom._

/** A view of a square grid, currently representing the [[SqCoord]] focus and the pixels/dx scale. */
class SqGridView(val r: Int, val c: Int, val pxScale: Double) extends Show2[SqCoord, Double]
{ def hCoord: SqCoord = SqCoord(r, c)
  def vec: Vec2 = hCoord.toVecReg
  def pt2: Pt2 = hCoord.toPt2Reg
  override def typeStr: String = "GridView"
  override def name1: String = "hCoord"
  inline override def show1: SqCoord = hCoord
  override def name2: String = "pxScale"
  inline override def show2: Double = pxScale
  override implicit def showT1: ShowT[SqCoord] = SqCoord.persistImplicit
  override implicit def showT2: ShowT[Double] = ShowT.doublePersistEv
  override def syntaxDepth: Int = 3
}

/** Companion object for [[SqGridView]] class. Contains factory apply method overloads and implicit Persist instance. */
object SqGridView
{ def apply(r: Int, c: Int, pxScale: Double = 50): SqGridView = new SqGridView(r, c, pxScale)
  def apply(sqCoord: SqCoord, pxScale: Double): SqGridView = new SqGridView(sqCoord.r, sqCoord.c, pxScale)

  /** Implicit [[Persist]] instance for SqGridView.  */
  implicit val persistImplicit: PersistShow2[SqCoord, Double, SqGridView] =
    PersistShow2[SqCoord, Double, SqGridView]("SqGridView", "sqCoord", "pxScale", apply(_, _))
}