################################################################################
# Automatically-generated file. Do not edit!
################################################################################

S_UPPER_SRCS += \
${addprefix $(ROOT)/src/, \
}

CPP_SRCS += \
${addprefix $(ROOT)/src/, \
}

CC_SRCS += \
${addprefix $(ROOT)/src/, \
}

C_SRCS += \
${addprefix $(ROOT)/src/, \
Score.c \
remchar.c \
}

C_UPPER_SRCS += \
${addprefix $(ROOT)/src/, \
}

CXX_SRCS += \
${addprefix $(ROOT)/src/, \
}

S_SRCS += \
${addprefix $(ROOT)/src/, \
}

# Each subdirectory must supply rules for building sources it contributes
src/%.o: $(ROOT)/src/%.c
	@echo 'Building file: $<'
	@echo gcc -O0 -g3 -Wall -c -fmessage-length=0 -o$@ $<
	@gcc -O0 -g3 -Wall -c -fmessage-length=0 -o$@ $< && \
	echo -n $(@:%.o=%.d) 'src/' > $(@:%.o=%.d) && \
	gcc -MM -MG -P -w -O0 -g3 -Wall -c -fmessage-length=0  $< >> $(@:%.o=%.d)
	@echo 'Finished building: $<'
	@echo ' '


