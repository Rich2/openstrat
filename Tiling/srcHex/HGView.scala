/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._

/** A view of a hex grid, currently representing the [[HCoord]] focus and the pixels per delta Cs scale. */
class HGView(val r: Int, val c: Int, val pixelsPerC: Double, val northUp: Boolean) extends Tell3[HCoord, Double, Boolean]
{ def hCoord: HCoord = HCoord(r, c)
  def vec: Vec2 = hCoord.toVecReg
  def pt2: Pt2 = hCoord.toPt2Reg
  override def typeStr: String = "HGView"
  override def name1: String = "hCoord"
  inline override def tell1: HCoord = hCoord
  override def name2: String = "cPScale"
  inline override def tell2: Double = pixelsPerC
  override def name3: String = "northUp"
  override def tell3: Boolean = northUp
  override implicit def show1: Show[HCoord] = HCoord.persistImplicit
  override implicit def show2: Show[Double] = Show.doubleEv
  override def show3: Show[Boolean] = Show.booleanEv
  override def tellDepth: Int = 3
}

/** Companion object for [[HGView]] class. Contains factory apply method overloads and implicit Persist instance. */
object HGView
{ def apply(r: Int, c: Int, cPScale: Double = 50, orientUp: Boolean = true): HGView = new HGView(r, c, cPScale, orientUp)
  def apply(hCoord: HCoord, pxScale: Double, orientUp: Boolean): HGView = new HGView(hCoord.r, hCoord.c, pxScale, orientUp)

  /** Implicit [[Show]] and [[Unshow]] type class instance / evidence for [[HGView]]. */
  implicit val persistEv: PersistTell3[HCoord, Double, Boolean, HGView] =
    PersistTell3[HCoord, Double, Boolean, HGView]("HGView", "hCoord", "cPScale", "northUp", apply, Some(true))
}