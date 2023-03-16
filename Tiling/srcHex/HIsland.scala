/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex

trait HInner

/** Exploratory trait. Will not pursue further for now. */
trait HIsland[OT, IT]
{ def outer: OT
  def inner: IT
}

trait HIslandColoured[OT <: Coloured, IT] extends HIsland[OT, IT] with Coloured
{ override def colour: Colour = outer.colour
}