package net.corda.nodeapi.internal.serialization.carpenter

import net.corda.nodeapi.internal.serialization.AllWhitelist
import net.corda.nodeapi.internal.serialization.amqp.DeserializationInput
import net.corda.nodeapi.internal.serialization.amqp.SerializerFactory
import net.corda.nodeapi.internal.serialization.amqp.TestSerializationOutput
import net.corda.nodeapi.internal.serialization.amqp.testDefaultFactoryNoEvolution
import org.junit.Test

class tcl : ClassLoader() {
    override fun loadClass(p0: String?): Class<*> {
        println(p0)
        return super.loadClass(p0)
    }
}

class CarpenterExceptionTests {
    companion object {
        val VERBOSE: Boolean get() = true
    }

    @Test
    fun carpenterExcpRethrownAsNSE() {
        data class C (val i: Int, val c: C?)

        val factory1 = SerializerFactory(AllWhitelist, tcl())
        val factory2 = SerializerFactory(AllWhitelist, tcl())

        val ser = TestSerializationOutput(VERBOSE, factory1).serializeAndReturnSchema(C(1, C(2, C(3,  null))))

        DeserializationInput(factory2).deserialize(ser.obj)

    }
}