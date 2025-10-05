/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDoc
import pWeb.*, wcode.*

/** Documentation page for ScalaOS Module." */
object ScalaOSPage extends OSDocumentationPage
{
  override def titleStr: String = "Scala Operating System"
  override def body: HtmlBody = HtmlBody(HtmlH1("Geom Module"), central)
  def central: HtmlDiv = HtmlDiv.classAtt("central", p1)

  val p1 = HtmlP("An operating system built from scratch with just a Linux Kernel. Don't know if this project wil ever get anywhere.",
  BashLine("sudo apt install libncurses-dev gawk flex bison openssl libssl-dev dkms libelf-dev libudev-dev libpci-dev libiberty-dev autoconf llvm"),
  BashLine("git clone git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux-stable.git"),
  BashLine("cd linux-stable"),
  BashLine("git checkout -b stable v6.16.9"),
  BashLine("make defconfig"),
  SpanLine("y to include n to exclude"),
  BashLine("sudo apt install libelf-dev"),
  BashLine("sudo apt install elfutils"),
  BashWithPrompt("root@yourcomputer:rootOfNewPartition", "mkdir boot"),
  BashWithPrompt("root@yourcomputer:rootOfNewPartition", "cp /dirs/linux-stable/vmlinuz boot"),
  BashWithPrompt("yourname@yourcomputer:/dirs/ScalaOS/cSrc", "gcc init.c -o ../target/init"),
  BashWithPrompt("root@yourcomputer:rootOfNewPartition", "mkdir sbin"),
  BashWithPrompt("root@yourcomputer:rootOfNewPartition", "cp /openstrat/ScalaOS/target/init sbin/"),
  )
}