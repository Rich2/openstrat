/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDoc
import pWeb.*

/** Documentation page for ScalaOS Module." */
object ScalaOSPage extends OSDocumentationPage
{
  override def titleStr: String = "Scala Operating System"
  override def body: HtmlBody = HtmlBody(HtmlH1("Geom Module"), central)
  def central: HtmlDiv = HtmlDiv.classAtt("central", str1)

  val str1 = """An operating system built from scratch with just a Linux Kernel. Don't know if this project wil ever get anywhere.
  <br>git clone git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux-stable.git
  <br>cd linux-stable
  <br>git checkout -b stable v6.16.9
  <br>make defconfig
  <br>y to include n to exclude
  <br>sudo apt install libelf-dev
  <br>sudo apt install elfutils"""
}