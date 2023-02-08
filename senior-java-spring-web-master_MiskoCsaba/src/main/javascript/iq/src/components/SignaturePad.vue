<template >

  <div id="signature-pad" >
    <h5>Sign your attached picture below</h5>
    <vueSignature ref="signature" :sigOption="option"></vueSignature>
    <button @click="clear">Clear Sign</button>
    <button @click="undo">Undo</button>
  </div>

</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import vueSignature from "vue-signature";

@Component({
  components: {
    vueSignature
  }
})

export default class SignaturePad extends Vue {
  signature = '';
  option = {
    penColor: "Navy",
    backgroundColor: "MintCream"
  };

  getSignature() {
    const signature = this.$refs.signature as any;
    const canvas = signature.$el.querySelector('canvas');
    this.signature = canvas.toDataURL();
    return this.signature;
  }

  clear() {
    (this.$refs.signature as any).clear();
  }

  undo() {
    (this.$refs.signature as any).undo();
  }
}
</script>

<style  lang="scss">

    h5 {
      margin: 15px;
    }

    .canvas {
      border: 2px solid rgb(178,178,178);
      border-radius: 5px;
      width: 60%;
      height: 40%;
      cursor: crosshair;
    }

    button{
      margin: 8px;
      border-radius: 5px;
    }

    button:hover{
      background-color: #42b983;
    }

</style>
