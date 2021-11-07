NAME		=	Main

ZIP_NAME	=	projet_THAI_MOSLEH_ZALUGAS.zip

DIR_SRC		=	src

DIR_OUT		=	bin

DIR_DOC		=	documentation

SRCS		=	$(wildcard $(DIR_SRC)/*.java)

GREEN		=	\033[1;32m

END			=	\033[0m

all:			$(NAME)

$(NAME):		$(DIR_OUT)
			@javac -d $(DIR_OUT) -cp $(DIR_SRC) $(SRCS)

run:			$(NAME)
			@echo "$(GREEN)Lancement de $(NAME)$(END)"
			@java -cp $(DIR_OUT) $(NAME)

$(DIR_OUT):
			@echo "$(GREEN)Création du dossier $(DIR_OUT)$(END)"
			@mkdir -p $(DIR_OUT)

jar:			$(NAME)
			@jar -cvf projet_THAI_MOSLEH_ZALUGAS.jar $(DIR_OUT)
			@echo "$(GREEN)Archive .jar créée.$(END)"

zip:			$(NAME)
			@zip -r $(ZIP_NAME) $(DIR_SRC)
			@echo "$(GREEN)Archive .zip créée.$(END)"

doc:			$(DIR_DOC)
			javadoc -d $(DIR_DOC) $(SRCS)
			@echo "$(GREEN)Documentation générée.$(END)"

$(DIR_DOC):
			@mkdir -p $(DIR_DOC)

clean:
			rm -f $(wildcard $(DIR_OUT)/*.class)
			rm -rf $(DIR_DOC)

re:			clean all

.PHONY:			doc
