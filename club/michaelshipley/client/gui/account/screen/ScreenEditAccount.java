// 
// Decompiled by Procyon v0.5.30
// 

package club.michaelshipley.client.gui.account.screen;

import club.michaelshipley.client.account.AccountManager;
import club.michaelshipley.client.account.Alt;
import club.michaelshipley.client.gui.account.AccountScreen;
import club.michaelshipley.client.gui.account.component.Button;
import club.michaelshipley.client.gui.account.component.GuiPasswordField;
import club.michaelshipley.utils.ClientUtils;
import club.michaelshipley.utils.RenderUtils;
import club.michaelshipley.utils.minecraft.GuiTextField;

public class ScreenEditAccount extends Screen
{
    private EditButton editButton;
    private GuiTextField emailText;
    private GuiPasswordField passText;
    private GuiTextField nameText;
    private Alt alt;
    
    public ScreenEditAccount(final Alt alt) {
        this.alt = alt;
        this.editButton = new EditButton();
        this.emailText = new GuiTextField(-10, ClientUtils.clientFont(), ClientUtils.mc().currentScreen.width / 2 - 60, ClientUtils.mc().currentScreen.height / 2 - 13 - 80, 120, 26);
        this.passText = new GuiPasswordField(-9, ClientUtils.mc().fontRendererObj, ClientUtils.mc().currentScreen.width / 2 - 60, ClientUtils.mc().currentScreen.height / 2 - 13 - 40, 120, 26);
        this.nameText = new GuiTextField(-8, ClientUtils.clientFont(), ClientUtils.mc().currentScreen.width / 2 - 60, ClientUtils.mc().currentScreen.height / 2 - 13, 120, 26);
        this.emailText.setText(alt.getEmail());
        this.passText.setText(alt.getPassword());
        this.nameText.setText(alt.getUsername());
    }
    
    @Override
    public void draw(final int mouseX, final int mouseY) {
        if (!this.emailText.isFocused() && !this.emailText.getText().equals("§7Username") && !this.emailText.getText().equals("§cCannot Be Blank") && this.emailText.getText().length() == 0) {
            this.emailText.setText("§7Username");
        }
        if (!this.passText.isFocused() && !this.passText.getText().equals("§7Password") && this.passText.getText().length() == 0) {
            this.passText.setText("§7Password");
        }
        if (!this.nameText.isFocused() && !this.nameText.getText().equals("§7Name") && this.nameText.getText().length() == 0) {
            this.nameText.setText("§7Name");
        }
        RenderUtils.rectangle(0.0, 0.0, ClientUtils.mc().currentScreen.width, ClientUtils.mc().currentScreen.height, -1072689136);
        this.editButton.draw(mouseX, mouseY);
        this.emailText.drawTextBox();
        this.passText.drawTextBox();
        this.nameText.drawTextBox();
    }
    
    @Override
    public void onClick(final int x, final int y, final int mouseButton) {
        if (this.emailText.getText().equals("§7Username") || this.emailText.getText().equals("§cCannot Be Blank")) {
            this.emailText.setText("");
        }
        if (this.passText.getText().equals("§7Password")) {
            this.passText.setText("");
        }
        if (this.nameText.getText().equals("§7Name")) {
            this.nameText.setText("");
        }
        if (this.editButton.isOver()) {
            this.editButton.onClick(mouseButton);
        }
        this.emailText.mouseClicked(x, y, mouseButton);
        this.passText.mouseClicked(x, y, mouseButton);
        this.nameText.mouseClicked(x, y, mouseButton);
    }
    
    @Override
    public void onKeyPress(final char c, final int key) {
        this.emailText.textboxKeyTyped(c, key);
        this.passText.textboxKeyTyped(c, key);
        this.nameText.textboxKeyTyped(c, key);
    }
    
    @Override
    public void update() {
        this.emailText.updateCursorCounter();
        this.passText.updateCursorCounter();
        this.nameText.updateCursorCounter();
    }
    
    public int getPositionInAltList(final Alt alt) {
        for (int i = 0; i < AccountManager.accountList.size() - 1; ++i) {
            if (AccountManager.accountList.get(i).equals(alt)) {
                return i;
            }
        }
        return 0;
    }
    
    private class EditButton extends Button
    {
        public EditButton() {
            super("Edit Account", ClientUtils.mc().currentScreen.width / 2 - 40, ClientUtils.mc().currentScreen.width / 2 + 40, ClientUtils.mc().currentScreen.height / 2 - 13 + 40, ClientUtils.mc().currentScreen.height / 2 + 13 + 40, -15921907, -16777216);
        }
        
        @Override
        public void onClick(final int button) {
            if (ScreenEditAccount.this.emailText.getText().length() == 0 || ScreenEditAccount.this.emailText.getText().equals("§7Username") || ScreenEditAccount.this.emailText.getText().equals("§cCannot Be Blank")) {
                ScreenEditAccount.this.emailText.setText("§cCannot Be Blank");
                return;
            }
            AccountManager.addAlt(ScreenEditAccount.this.getPositionInAltList(ScreenEditAccount.this.alt), new Alt(ScreenEditAccount.this.emailText.getText(), ScreenEditAccount.this.nameText.getText(), ScreenEditAccount.this.passText.getText()));
            AccountManager.removeAlt(ScreenEditAccount.this.alt);
            AccountScreen.getInstance().currentScreen = null;
            AccountManager.save();
            AccountScreen.getInstance().initGui();
            AccountScreen.getInstance().info = "§aAlt Edited";
        }
    }
}
