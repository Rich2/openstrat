/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

trait RectLen2Graphic extends RectangleLen2Graphic
{
  override def shape: RectLen2[PtLen2]
}

trait RectLen2Fill extends RectangleLen2Fill