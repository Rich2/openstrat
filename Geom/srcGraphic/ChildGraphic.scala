/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import annotation.unchecked.uncheckedVariance
trait ParentGraphic[+ST]
{
  def childs: RArr[ST => GraphicElems] @uncheckedVariance
}

trait ParentGraphic2[+ST] extends ParentGraphic[ST]
{
  def shape: ST
  def childsGraphics: GraphicElems = childs.flatMap(cs => cs(shape))
}

trait ChildGraphic[PT]
{
  def graphics(parent: PT): RArr[Graphic2Elem]
}

/*object ChildGraphic
{
  def apply[ST]()
}*/