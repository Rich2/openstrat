/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._

/** A view of a hex grid, currently representing the [[HCoord]] focus and the pixels per delta Cs scale. */
class HGView(val r: Int, val c: Int, val pixelsPerC: Double) extends Tell2[HCoord, Double]
{ def hCoord: HCoord = HCoord(r, c)
  def vec: Vec2 = hCoord.toVecReg
  def pt2: Pt2 = hCoord.toPt2Reg
  override def typeStr: String = "HGView"
  override def name1: String = "hCoord"
  inline override def show1: HCoord = hCoord
  override def name2: String = "cPScale"
  inline override def show2: Double = pixelsPerC
  override implicit def persist1: Show[HCoord] = HCoord.persistImplicit
  override implicit def persist2: Show[Double] = Show.doublePersistEv
  override def syntaxDepth: Int = 3
}

/** Companion object for [[HGView]] class. Contains factory apply method overloads and implicit Persist instance. */
object HGView
{ def apply(r: Int, c: Int, cPScale: Double = 50): HGView = new HGView(r, c, cPScale)
  def apply(hCoord: HCoord, pxScale: Double): HGView = new HGView(hCoord.r, hCoord.c, pxScale)

  /** Implicit [[Persist]] instance for HGridView.  */
  implicit val persistImplicit: PersistTell2[HCoord, Double, HGView] =
    PersistTell2[HCoord, Double, HGView]("HGView", "hCoord", "cPScale", apply(_, _))
}