/* Copyright 2016 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb
import reflect.ClassTag

trait InputLikeUpdater extends HtmlInputLike
{
  def page: HtmlPageUpdater
}

trait UpdaterText extends InputLikeUpdater
{
  
}