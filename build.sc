// build.sc
import mill._, scalalib._, scalajslib._, scalanativelib._

trait Common extends ScalaModule
{ def version = "0.0.4snap"  
  def scalacOptions = Seq("-feature", "-language:higherKinds,implicitConversions", "-deprecation", "-Ywarn-value-discard", "-target:jvm-1.8", "-encoding", "UTF-8", "-unchecked", "-Xlint")
}

trait PlatformsModule extends ScalaModule with Common
{ outer =>  
  def scalaVersion = "2.13.0"
  def sources = T.sources(millSourcePath / 'src, millSourcePath / 'jvm / 'src)

  trait InnerJs extends ScalaJSModule with Common
  { def scalaVersion = "2.13.0"
	  def scalaJSVersion = "0.6.28" 
	  def sources = T.sources(outer.millSourcePath / 'src, millSourcePath / 'src)	  
	  def ivyDeps = outer.ivyDeps() ++  Agg(ivy"org.scala-js::scalajs-dom_sjs0.6:0.9.7")
  }

  trait InnerNative extends ScalaNativeModule with Common
  { def scalaVersion = "2.11.12"
    def scalaNativeVersion = "0.3.8"  
	  def sources = T.sources(outer.millSourcePath / 'src, outer.millSourcePath / 'srcNat)
	  def ivyDeps = outer.ivyDeps() //++ ivyNat()	 
  }

  trait InnerTests extends Tests
  { def ivyDeps = Agg(ivy"com.lihaoyi::utest:0.6.9")
    def testFrameworks = Seq("utest.runner.Framework") 
    def sources = T.sources(millSourcePath / 'src)  
  }
}

object UtilMacros extends PlatformsModule
{ def ivyDeps = Agg(ivy"${scalaOrganization()}:scala-reflect:${scalaVersion()}")
  object js extends InnerJs
  object Nat extends InnerNative  
}

object Util extends PlatformsModule
{ def moduleDeps = Seq(UtilMacros)

  object test extends InnerTests

  object js extends InnerJs {  def moduleDeps = Seq(UtilMacros.js)  }

  object Nat extends InnerNative  
}

object Graphic extends PlatformsModule
{ 
  def moduleDeps = Seq(Util)  
  object test extends InnerTests
  
  object js extends InnerJs {  def moduleDeps = Seq(Util.js)  }
  object Nat extends InnerNative
}

object World extends PlatformsModule
{
  def moduleDeps = Seq(Graphic)  

  object test extends InnerTests
      
  object js extends InnerJs { def moduleDeps = Seq(Graphic.js) }
  object Nat extends InnerNative
}

object Strat extends PlatformsModule
{
  def moduleDeps = Seq(World)  

  object test extends InnerTests
      
  object js extends InnerJs { def moduleDeps = Seq(World.js) }
  object Nat extends InnerNative
}

object Learn extends PlatformsModule
{
  def moduleDeps = Seq(Strat)  

  object test extends InnerTests
      
  object js extends InnerJs { def moduleDeps = Seq(Strat.js) }
  object Nat extends InnerNative
}

object Dev extends PlatformsModule
{
  def moduleDeps = Seq(Learn)
  def mainClass = Some("ostrat.pFx.DevApp")
  def sources = T.sources(millSourcePath / 'src, millSourcePath / 'jvm / 'src)
  def resources = T.sources(millSourcePath / 'mine)

  object js extends InnerJs
  { def moduleDeps = Seq(Learn.js)
    def sources = T.sources(millSourcePath / 'src, Dev.millSourcePath / 'srcLearn)
  } 
}

def run() = Dev.runBackground()
def test = Util.test
def jsfast = Dev.js.fastOpt
def jsfull = Dev.js.fullOpt