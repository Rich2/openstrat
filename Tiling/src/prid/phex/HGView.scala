/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._

/** A view of a hex grid, currently representing the [[HCoord]] focus and the pixels/dx scale. */
class HGView(val r: Int, val c: Int, val pxScale: Double) extends Show2[HCoord, Double]
{ def hCoord: HCoord = HCoord(r, c)
  def vec: Vec2 = hCoord.toVecReg
  def pt2: Pt2 = hCoord.toPt2Reg
  override def typeStr: String = "HGView"
  override def name1: String = "hCoord"
  inline override def show1: HCoord = hCoord
  override def name2: String = "pxScale"
  inline override def show2: Double = pxScale
  override implicit def showT1: ShowT[HCoord] = HCoord.persistImplicit
  override implicit def showT2: ShowT[Double] = ShowT.doublePersistEv
  override def syntaxDepth: Int = 3
}

/** Companion object for [[HGView]] class. Contains factory apply method overloads and implicit Persist instance. */
object HGView
{ def apply(r: Int, c: Int, pxScale: Double = 50): HGView = new HGView(r, c, pxScale)
  def apply(hCoord: HCoord, pxScale: Double): HGView = new HGView(hCoord.r, hCoord.c, pxScale)

  /** Implicit [[Persist]] instance for HGridView.  */
  implicit val persistImplicit: PersistShow2[HCoord, Double, HGView] =
    PersistShow2[HCoord, Double, HGView]("HGView", "hCoord", "pxScale", apply(_, _))
}