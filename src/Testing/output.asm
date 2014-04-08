.data
input:
	.space	1
string6:
	.asciiz "Pull the trigger\n"
string5:
	.asciiz "Press space to continue...\n"
string8:
	.asciiz "You also see a small path meandering into the distance."
string7:
	.asciiz "You see a large tree with a large wooden door embedded in the roots.\n\n"
string2:
	.asciiz "You open your eyes."
string1:
	.asciiz "ERROR!!!"
string4:
	.asciiz "You have no idea where you are."
string3:
	.asciiz "You appear to be in a large forest."
string9:
	.asciiz ""
string14:
	.asciiz "You open the door. It is very dark."
string15:
	.asciiz "You wander down the path. It is very dark."
string16:
	.asciiz "You can barely see a thing..."
string17:
	.asciiz "You get eaten by a grue."
string10:
	.asciiz "[1] "
string11:
	.asciiz "Open the door."
string12:
	.asciiz "[2] "
string13:
	.asciiz "Wander down a path"
string18:
	.asciiz "You fall into the bottomless pit."
string0:
	.asciiz "\n"

jt0:
	.word jr0
jt1:
	.word jc1_1, jc1_2, jr1
jt2:
	.word jr2

.text
.globl	main

main:
		li	$v0, 4
		la	$a0, string2
		syscall
		la	$a0, string0
		syscall
		la	$a0, string3
		syscall
		la	$a0, string0
		syscall
		la	$a0, string4
		syscall
		la	$a0, string0
		syscall
		la	$a0, string0
		syscall
		la	$a0, string5
		syscall
input0:
		li		$t0, 0					# $t0 = Upper Bound of input.
		li	$v0, 12
		syscall
		add		$s1, $v0, $0			# s1 = input
		addi	$v0, $v0, -49
		bltz	$v0, input0_x			# Jump to input0_x if $v0 < 0.
		sub		$t1, $v0, $t0
		bgez	$t1, input0_x			# Jump to input0_x if $v0 > max Input.
		add		$s0, $v0, $t0			# $s0 = [1, maxInput]
		addi	$s0, $s0, -1			# $s0 = [0, maxInput - 1]
		j		input0_end
input0_x:
		add		$s0, $t0, $0			# $s0 = maxInput
input0_end:
		li	$v0, 4
		la	$a0, string0
		syscall
		sll		$t0, $s0, 2
		lw		$t0, jt0($t0)
		jr		$t0
jr0:
		li		$t0, 32
		beq		$s1, $t0, jr0_0
		j		input0
jr0_0:
		la	$a0, string6
		syscall
		j		start
je0:

start:
		li	$v0, 4
		la	$a0, string7
		syscall
		la	$a0, string8
		syscall
		la	$a0, string9
		syscall
		la	$a0, string0
		syscall
		la	$a0, string10
		syscall
		la	$a0, string11
		syscall
		la	$a0, string0
		syscall
		la	$a0, string12
		syscall
		la	$a0, string13
		syscall
		la	$a0, string0
		syscall
input1:
		li		$t0, 2					# $t0 = Upper Bound of input.
		li	$v0, 12
		syscall
		add		$s1, $v0, $0			# s1 = input
		addi	$v0, $v0, -49
		bltz	$v0, input1_x			# Jump to input1_x if $v0 < 0.
		sub		$t1, $v0, $t0
		bgez	$t1, input1_x			# Jump to input1_x if $v0 > max Input.
		add		$s0, $v0, $t0			# $s0 = [1, maxInput]
		addi	$s0, $s0, -1			# $s0 = [0, maxInput - 1]
		j		input1_end
input1_x:
		add		$s0, $t0, $0			# $s0 = maxInput
input1_end:
		li	$v0, 4
		la	$a0, string0
		syscall
		sll		$t0, $s0, 2
		lw		$t0, jt1($t0)
		jr		$t0
jc1_1:
		la	$a0, string14
		syscall
		la	$a0, string0
		syscall
		j		je1
jc1_2:
		la	$a0, string15
		syscall
		la	$a0, string0
		syscall
		j		je1
jr1:
		j		input1
je1:
		j		grue

grue:
		li	$v0, 4
		la	$a0, string16
		syscall
		la	$a0, string0
		syscall
		la	$a0, string17
		syscall
		la	$a0, string0
		syscall

bottomless:
		la	$a0, string18
		syscall
		la	$a0, string0
		syscall
		la	$a0, string5
		syscall
input2:
		li		$t0, 0					# $t0 = Upper Bound of input.
		li	$v0, 12
		syscall
		add		$s1, $v0, $0			# s1 = input
		addi	$v0, $v0, -49
		bltz	$v0, input2_x			# Jump to input2_x if $v0 < 0.
		sub		$t1, $v0, $t0
		bgez	$t1, input2_x			# Jump to input2_x if $v0 > max Input.
		add		$s0, $v0, $t0			# $s0 = [1, maxInput]
		addi	$s0, $s0, -1			# $s0 = [0, maxInput - 1]
		j		input2_end
input2_x:
		add		$s0, $t0, $0			# $s0 = maxInput
input2_end:
		li	$v0, 4
		la	$a0, string0
		syscall
		sll		$t0, $s0, 2
		lw		$t0, jt2($t0)
		jr		$t0
jr2:
		li		$t0, 32
		beq		$s1, $t0, jr2_0
		j		input2
jr2_0:
		j		bottomless
je2:
error:
		li	$v0, 4
		la	$a0, string1
		syscall
