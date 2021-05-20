package com.example.desafiodesignpatternsn1.verificacao

class VerificacaoCNPJ : Verificacao {
    override fun validate(s: String): Boolean {
        return validateCNPJLength(s) && validateCNPJRepeatedNumbers(s)
                && validateCNPJVerificationDigit(true, s)
                && validateCNPJVerificationDigit(false, s)
    }

    private fun validateCNPJLength(cnpj: String) = cnpj.length == 14

    private fun validateCNPJRepeatedNumbers(cnpj: String): Boolean {
        return (0..9)
            .map { it.toString().repeat(14) }
            .map { cnpj == it }
            .all { !it }
    }

    private fun validateCNPJVerificationDigit(fDigit: Boolean, cnpj: String): Boolean {
        val startPos = when (fDigit) {
            true -> 11
            else -> 12
        }
        val weightOffset = when (fDigit) {
            true -> 0
            false -> 1
        }
        val sum = (startPos downTo 0).fold(0) { acc, pos ->
            val weight = 2 + ((11 + weightOffset - pos) % 8)
            val num = cnpj[pos].toString().toInt()
            val sum = acc + (num * weight)
            sum
        }
        val result = sum % 11
        val expectedDigit = when (result) {
            0, 1 -> 0
            else -> 11 - result
        }

        val actualDigit = cnpj[startPos +1].toString().toInt()

        return expectedDigit == actualDigit
    }

}